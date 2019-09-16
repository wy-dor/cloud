package com.learning.cloud.bizData.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.learning.cloud.bizData.dao.SyncBizDataMediumDao;
import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import com.learning.cloud.bizData.service.BizDataMediumService;
import com.learning.cloud.dept.department.dao.DepartmentDao;
import com.learning.cloud.dept.department.entity.Department;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.index.dao.AuthAppInfoDao;
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

    @Autowired
    private AuthAppInfoDao authAppInfoDao;

    @Override
    public JsonResult initBizDataMedium(SyncBizDataMedium syncBizDataMedium) throws Exception {
        List<SyncBizDataMedium> allBizDataMedium = syncBizDataMediumDao.getAllBizDataMedium(syncBizDataMedium);
        if(allBizDataMedium == null || allBizDataMedium.size() == 0){
            return null;
        }
        for (SyncBizDataMedium sbdm : allBizDataMedium) {
            Long id = sbdm.getId();
            String corpId = sbdm.getCorpId();
            String accessToken = authenService.getAccessToken(corpId);
            Integer schoolId;
            School schoolByCorpId = schoolDao.getSchoolByCorpId(corpId);
            if(schoolByCorpId == null){
                schoolId = -1;
            }else{
                schoolId = schoolByCorpId.getId();
            }
            Integer bizType = sbdm.getBizType();
            Map<String, Object> bizDataParse = (Map<String, Object>) JSON.parse(sbdm.getBizData());
            String syncAction = (String) bizDataParse.get("syncAction");
            if(bizType == 13){
                //员工同步
                //todo
                //员工删除
                if(syncAction.equals("user_leave_org")){
                    continue;
                }
                String userId = (String) bizDataParse.get("userid");
                Integer roleType = 5;

                //设置角色类型
                //todo
                //deptId范围
                List<Integer> departmentList = (List<Integer>) bizDataParse.get("department");
                int size = departmentList.size();
                Integer lastDeptId = departmentList.get(size - 1);

                OapiDepartmentGetResponse deptDetail = deptService.getDeptDetail(lastDeptId + "", accessToken);
                String deptName = deptDetail.getName();
                if(deptName.equals("老师")){
                    roleType = 3;
                }else if(deptName.equals("学生")){
                    roleType = 4;
                }else if(deptName.equals("家长")){
                    roleType = 2;
                }
                deptService.userSaveByRole(schoolId,corpId,null,userId,roleType,accessToken);

            }else if(bizType == 14){
                //todo
                //部门删除
                if (syncAction.equals("org_dept_remove")) {
                    continue;
                }
                String deptId = ((Integer) bizDataParse.get("id")).toString();
                Department dept = new Department();
                dept.setDeptId(deptId);
                dept.setCorpId(corpId);
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
            //标志已操作
            syncBizDataMediumDao.updateStatus(id);
        }
        return JsonResultUtil.success();
    }


}
