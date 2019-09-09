package com.learning.cloud.bizData.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.learning.cloud.bizData.dao.SyncBizDataMediumDao;
import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import com.learning.cloud.bizData.service.BizDataMediumService;
import com.learning.cloud.dept.department.dao.DepartmentDao;
import com.learning.cloud.dept.department.entity.Department;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.admin.dao.AdministratorDao;
import com.learning.cloud.user.admin.entity.Administrator;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BizDataMediumServiceImpl implements BizDataMediumService {

    @Autowired
    private SyncBizDataMediumDao syncBizDataMediumDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private AdministratorDao administratorDao;

    @Autowired
    private DeptService deptService;

    @Autowired
    private AuthenService authenService;

    @Override
    public JsonResult initBizDataMedium(String corpId) throws Exception {

        List<SyncBizDataMedium> allBizDataMedium = syncBizDataMediumDao.getAllBizDataMedium(corpId);
        if(allBizDataMedium == null || allBizDataMedium.size() == 0){
            return null;
        }
        for (SyncBizDataMedium syncBizDataMedium : allBizDataMedium) {
            Long id = syncBizDataMedium.getId();
            //先标志已操作，不管任意类型
            syncBizDataMediumDao.updateStatus(id);
            String corpId_1 = syncBizDataMedium.getCorpId();
            Integer schoolId;
            School schoolByCorpId = schoolDao.getSchoolByCorpId(corpId_1);
            if(schoolByCorpId == null){
                schoolId = -1;
            }else{
                schoolId = schoolByCorpId.getId();
            }
            Integer bizType = syncBizDataMedium.getBizType();
            Map<String, Object> bizDataParse = (Map<String, Object>) JSON.parse(syncBizDataMedium.getBizData());
            String syncAction = (String) bizDataParse.get("syncAction");
            if(bizType == 13){
                //员工同步
                //todo
                //员工删除
                if(syncAction.equals("user_leave_org")){
                    continue;
                }
                String userId = (String) bizDataParse.get("userid");
                String userName = (String) bizDataParse.get("name");
                String unionId = (String) bizDataParse.get("unionid");
                User user = new User();
                user.setUserId(userId);
                user.setUnionId(unionId);
                user.setUserName(userName);
                user.setSchoolId(schoolId);
                Boolean isAdmin = false;
                if(bizDataParse.get("isAdmin") != null){
                    isAdmin = (Boolean) bizDataParse.get("isAdmin");
                }
                Integer roleType = 0;
                if(isAdmin){
                    Administrator a = new Administrator();
                    a.setName(userName);
                    a.setUserId(userId);
                    a.setSchoolId(schoolId);
                    Administrator byAdm = administratorDao.getByAdm(a);
                    //todo
                    if(byAdm == null){
                        administratorDao.insert(a);
                    }else{
                        administratorDao.updateName(a);
                    }
                }
                //设置角色类型
                //todo
                //deptId范围
                List<Integer> departmentList = (List<Integer>) bizDataParse.get("department");
                int size = departmentList.size();
                Integer lastDeptId = departmentList.get(size - 1);
                if(!isAdmin && (lastDeptId == 1)){
                    roleType = 0;
                }else{
                    String accessToken = authenService.getAccessToken(corpId_1);
                    OapiDepartmentGetResponse deptDetail = deptService.getDeptDetail(lastDeptId + "", accessToken);
                    String deptName = deptDetail.getName();
                    if(isAdmin){
                        roleType = 1;
                    }else if(deptName.equals("老师")){
                        roleType = 3;
                    }else if(deptName.equals("学生")){
                        roleType = 4;
                    }else if(deptName.equals("家长")){
                        roleType = 2;
                    }else{
                        roleType = 5;
                    }
                }
                user.setAvatar((String) bizDataParse.get("avatar"));
                user.setCorpId(corpId_1);
                if((Boolean) bizDataParse.get("active")){
                    user.setActive((short)1);
                }else{
                    user.setActive((short)0);
                }

                user.setRoleType(roleType);
                user.setCorpId(corpId_1);
                User user1 = userDao.getBySchoolRoleIdentity(user);
                if(user1 == null){
                    userDao.insert(user);
                }else{
                    //todo
                    //角色修改所致结构变化
                    userDao.updateWithSpecificRole(user);
                }

            }else if(bizType == 14){
                //todo
                //部门删除
                if (syncAction.equals("org_dept_remove")) {
                    continue;
                }
                String deptId = ((Integer) bizDataParse.get("id")).toString();
                Department dept = new Department();
                dept.setDeptId(deptId);
                dept.setCorpId(corpId_1);
                dept.setName((String) bizDataParse.get("name"));
                dept.setParentId(((Integer) bizDataParse.get("parentid")).toString());
                Boolean outerDept = false;
                if(bizDataParse.get("outerDept") != null){
                    outerDept = (Boolean) bizDataParse.get("outerDept");
                }
                if(outerDept){
                    dept.setOuterDept((short)1);
                }else{
                    dept.setOuterDept((short)0);
                }
                dept.setDeptManagerUseridList((String) bizDataParse.get("deptManagerUseridList"));
                if((Boolean) bizDataParse.get("groupContainSubDept")){
                    dept.setGroupContainSubDept((short)1);
                }else{
                    dept.setGroupContainSubDept((short)0);
                }
                //部门同步
                Department byDeptId = departmentDao.getByDeptId(deptId);
                if(byDeptId == null){
                    departmentDao.insert(dept);
                }else if(syncAction.equals("org_dept_modify")){
                    departmentDao.update(dept);
                }

            }else if(bizType == 16){
                //企业同步

            }
        }
        return JsonResultUtil.success();
    }


}
