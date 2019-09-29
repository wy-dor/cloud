package com.learning.cloud.bizData.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.learning.cloud.bizData.dao.SyncBizDataMediumDao;
import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import com.learning.cloud.bizData.service.BizDataMediumService;
import com.learning.cloud.dept.department.dao.DepartmentDao;
import com.learning.cloud.dept.department.entity.Department;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BizDataMediumServiceImpl implements BizDataMediumService {

    @Autowired
    private SyncBizDataMediumDao syncBizDataMediumDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private DeptService deptService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public JsonResult initBizDataMedium(SyncBizDataMedium syncBizDataMedium) throws Exception {
        List<SyncBizDataMedium> allBizDataMedium = syncBizDataMediumDao.getAllBizDataMedium(syncBizDataMedium);
        if (allBizDataMedium == null || allBizDataMedium.size() == 0) {
            return null;
        }
        for (SyncBizDataMedium sbdm : allBizDataMedium) {
            Long id = sbdm.getId();
            String corpId = sbdm.getCorpId();
            String accessToken = authenService.getAccessToken(corpId);
            if (accessToken == null) {
                continue;
            }
            Integer schoolId;
            School schoolByCorpId = schoolDao.getSchoolByCorpId(corpId);
            if (schoolByCorpId == null) {
                schoolId = -1;
            } else {
                schoolId = schoolByCorpId.getId();
            }
            Integer bizType = sbdm.getBizType();
            Map<String, Object> bizDataParse = (Map<String, Object>) JSON.parse(sbdm.getBizData());
            String syncAction = (String) bizDataParse.get("syncAction");
            if (bizType == 13) {
                //员工同步
                //todo
                //员工删除
                if (syncAction.equals("user_leave_org")) {
                    syncBizDataMediumDao.updateStatus(id);
                    continue;
                }
                //{"errcode":0,"unionid":"SiSRKI4KSk39fTOtTVTnsGwiEiE","syncAction":"user_active_org","userid":"1569277724148",
                // "isLeaderInDepts":"{114360989:false}","isBoss":false,"isSenior":false,"department":[114360989],
                // "orderInDepts":"{114360989:176348740195815512}","dingId":"$:LWCP_v1:$thWRYFwvvNF/BmcFZpoIBMKPhdliWOHx",
                // "errmsg":"ok","active":true,"avatar":"","isAdmin":false,"tags":{"guardian":["114461272"]},"isHide":false,"name":"刘淇妈妈"}
                //返回错误码继续跳过该条
                Integer errcode = (Integer)bizDataParse.get("errcode");
                if(errcode != 0){
                    syncBizDataMediumDao.updateStatus(id);
                    continue;
                }
                String userId = bizDataParse.get("userid").toString();
                Boolean isAdmin = (Boolean) bizDataParse.get("isAdmin");
                String avatar = bizDataParse.get("avatar").toString();
                Boolean active = (Boolean) bizDataParse.get("active");
                String name = bizDataParse.get("name").toString();
                String unionid = bizDataParse.get("unionid").toString();

                OapiUserListbypageResponse.Userlist apiUser = new OapiUserListbypageResponse.Userlist();
                apiUser.setActive(active);
                apiUser.setAvatar(avatar);
                apiUser.setIsAdmin(isAdmin);
                apiUser.setName(name);
                apiUser.setUnionid(unionid);
                apiUser.setUserid(userId);
                Integer roleType = 5;

                //设置角色类型
                //todo
                //deptId范围
                List<Integer> departmentList = (List<Integer>) bizDataParse.get("department");
                int size = departmentList.size();
                Long lastDeptId = departmentList.get(size - 1).longValue();

                OapiDepartmentGetResponse deptDetail = null;
                try {
                    deptDetail = deptService.getDeptDetail(lastDeptId + "", accessToken);
                } catch (ApiException e) {
                    log.info("biz_data_id:"+id.toString()+"/n"+e.getErrMsg());
                    syncBizDataMediumDao.updateStatus(id);
                    continue;
                }
                if(deptDetail == null){
                    syncBizDataMediumDao.updateStatus(id);
                    continue;
                }
                String deptName = deptDetail.getName();
                GradeClass gc = new GradeClass();
                Integer campusId = null;
                Integer classId = null;
                Integer bureauId = null;
                int i = 1;
                if (deptName.equals("老师")) {
                    roleType = 3;
                    gc.setTDeptId(lastDeptId);
                } else if (deptName.equals("学生")) {
                    roleType = 4;
                    gc.setTDeptId(lastDeptId);
                } else if (deptName.equals("家长")) {
                    roleType = 2;
                    gc.setPDeptId(lastDeptId);
                } else {
                    i = 0;
                }
                String classIdStr = "";
                if (i == 1) {
                    List<GradeClass> byGradeClass = gradeClassDao.getByGradeClass(gc);
                    if (byGradeClass != null && byGradeClass.size() > 0) {
                        GradeClass gradeClass = byGradeClass.get(0);
                        campusId = gradeClass.getCampusId();
                        classId = gradeClass.getId();
                        bureauId = gradeClass.getBureauId();
                        classIdStr = classId.toString();
                    }else{
                        //当前班级还未同步
                    }
                    if(roleType == 3){
                        String classIds = "";
                        roleType = 3;
                        Teacher teacher = new Teacher();
                        teacher.setTeacherName(name);
                        teacher.setUserId(userId);
                        teacher.setCampusId(campusId);
                        teacher.setSchoolId(schoolId);
                        teacher.setBureauId(bureauId);
                        Teacher t = teacherDao.getTeacherInSchool(teacher);
                        if (t == null) {
                            teacher.setClassIds(classIdStr);
                            teacherDao.insert(teacher);
                        } else {
                            //获取到classId才进行更新
                            if(!classIdStr.equals("")){
                                //判断老师所在班级是否存在
                                classIds = t.getClassIds();
                                String idsStr = "," + t.getClassIds() + ",";
                                if (!idsStr.contains("," + classIdStr + ",")) {
                                    StringBuilder sb = new StringBuilder(classIds);
                                    sb.append("," + classIdStr);
                                    teacher.setClassIds(sb.toString());
                                }
                            }
                            teacherDao.update(teacher);
                        }
                    } else if(roleType == 4){
                        Student student = new Student();
                        student.setUserId(userId);
                        student.setStudentName(name);
                        student.setClassId(classId);
                        student.setCampusId(campusId);
                        student.setSchoolId(schoolId);
                        student.setBureauId(bureauId);
                        Student s = studentDao.getByUserId(userId);
                        if (s == null) {
                            studentDao.insert(student);
                        } else {
                            studentDao.update(student);
                        }
                    }else if(roleType == 2){
                        String classIds = "";
                        Parent parent = new Parent();
                        parent.setUserId(userId);
                        parent.setParentName(name);
                        parent.setCampusId(campusId);
                        parent.setSchoolId(schoolId);
                        parent.setBureauId(bureauId);
                        Parent p = parentDao.getParentInSchool(parent);
                        if (p == null) {
                            parent.setClassId(classIdStr);
                            parentDao.insert(parent);
//                        parentId = parent.getId();
                        } else {
//                        parentId = p.getId();
                            //获取到classId才进行更新
                            if(!classIdStr.equals("")){
                                //判断班级是否已存在
                                classIds = p.getClassId();
                                String idsStr = "," + p.getClassId() + ",";
                                if (!idsStr.contains("," + classIdStr + ",")) {
                                    StringBuilder sb = new StringBuilder(classIds);
                                    sb.append("," + classIdStr);
                                    parent.setClassId(sb.toString());
                                }
                            }
                            parentDao.update(parent);
                        }
                    }
                }
                deptService.userSaveByRole(schoolId, corpId, campusId, apiUser, roleType, accessToken);
            } else if (bizType == 14) {
                //todo
                //部门删除
                if (syncAction.equals("org_dept_remove")) {
                    syncBizDataMediumDao.updateStatus(id);
                    continue;
                }
                String deptId = ((Integer) bizDataParse.get("id")).toString();
                Department dept = new Department();
                dept.setDeptId(deptId);
                dept.setCorpId(corpId);
                dept.setName((String) bizDataParse.get("name"));
                dept.setParentId(((Integer) bizDataParse.get("parentid")).toString());
                Boolean outerDept = false;
                if (bizDataParse.get("outerDept") != null) {
                    outerDept = (Boolean) bizDataParse.get("outerDept");
                }
                if (outerDept) {
                    dept.setOuterDept((short) 1);
                } else {
                    dept.setOuterDept((short) 0);
                }
                dept.setDeptManagerUseridList((String) bizDataParse.get("deptManagerUseridList"));
                if ((Boolean) bizDataParse.get("groupContainSubDept")) {
                    dept.setGroupContainSubDept((short) 1);
                } else {
                    dept.setGroupContainSubDept((short) 0);
                }
                //部门同步
                Department byDeptId = departmentDao.getByDeptId(deptId);
                if (byDeptId == null) {
                    departmentDao.insert(dept);
                } else if (syncAction.equals("org_dept_modify")) {
                    departmentDao.update(dept);
                }

            } else if (bizType == 16) {
                //企业同步

            }
            syncBizDataMediumDao.updateStatus(id);
        }
        return JsonResultUtil.success();
    }


}
