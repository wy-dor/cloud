package com.learning.cloud.bizData.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListParentDeptsByDeptResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.learning.cloud.bizData.dao.SyncBizDataMediumDao;
import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import com.learning.cloud.bizData.service.BizDataMediumService;
import com.learning.cloud.dept.campus.dao.CampusDao;
import com.learning.cloud.dept.campus.entity.Campus;
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
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private CampusDao campusDao;

    @Override
    public JsonResult initBizDataMedium(SyncBizDataMedium syncBizDataMedium) throws Exception {
        List<SyncBizDataMedium> allBizDataMedium = syncBizDataMediumDao.getAllBizDataMedium(syncBizDataMedium);
        if (allBizDataMedium == null || allBizDataMedium.size() == 0) {
            return null;
        }
        LOOP:
        for (SyncBizDataMedium sbdm : allBizDataMedium) {
            Long id = sbdm.getId();
            String corpId = sbdm.getCorpId();
            String accessToken = authenService.getAccessToken(corpId);
            if (accessToken == null) {
                continue;
            }
            Integer schoolId = -1;
            Integer bureauId = -1;
            School schoolByCorpId = schoolDao.getSchoolByCorpId(corpId);
            if (schoolByCorpId != null) {
                schoolId = schoolByCorpId.getId();
                Integer bId = schoolByCorpId.getBureauId();
                if(bId != null){
                    bureauId = bId;
                }
            }
            Integer bizType = sbdm.getBizType();
            Map<String, Object> bizDataParse = (Map<String, Object>) JSON.parse(sbdm.getBizData());
            String syncAction = (String) bizDataParse.get("syncAction");
            if (bizType == 13) {
                //员工同步
                if (syncAction.equals("user_leave_org")) {
                    syncBizDataMediumDao.updateStatus(id);
                    continue LOOP;
                }

                //返回错误码继续跳过该条
                Integer errcode = (Integer)bizDataParse.get("errcode");
                if(errcode != 0){
                    syncBizDataMediumDao.updateStatus(id);
                    continue LOOP;
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

                //因为一次可能会有多个部门的变化，所以使用tags判断其身份，对其信息进行保存
                //一次性添加两个人员在两个部门的变化时
                Object tagsMap = bizDataParse.get("tags");
                //存在tags则一定为老师家长学生角色？
                //多重身份 家长+老师？
                Integer campusId = null;
                if(tagsMap != null){
                    Map<String, List<String>> parse = (Map<String, List<String>>) JSON.parse(tagsMap.toString());
                    //学生只能有一种身份，且user表中不用存储学生信息
                    if(parse.get("student") != null){
                        List<String> studentDepts = parse.get("student");
                        roleType = 4;
                        Map<String, Object> map = getClassInfo(studentDepts);
                        campusId = (Integer) map.get("campusId");

                        Student student = new Student();
                        student.setUserId(userId);
                        student.setStudentName(name);
                        student.setClassId((Integer) map.get("classStrs"));
                        student.setCampusId(campusId);
                        student.setSchoolId(schoolId);
                        student.setBureauId(bureauId);
                        Student s = studentDao.getByUserId(userId);
                        if (s == null) {
                            studentDao.insert(student);
                        } else {
                            studentDao.update(student);
                        }
                    }else {
                        //多重身份解决
                        if(parse.get("teacher") != null || parse.get("headmaster") != null){
                            List<String> teacherDepts = parse.get("teacher");
                            List<String> teacherDepts_1 = parse.get("headmaster");

                            teacherDepts.addAll(teacherDepts_1);
                            roleType = 3;

                            Map<String, Object> map = getClassInfo(teacherDepts);
                            campusId = (Integer) map.get("campusId");

                            Teacher teacher = new Teacher();
                            teacher.setTeacherName(name);
                            teacher.setUserId(userId);
                            teacher.setCampusId(campusId);
                            teacher.setSchoolId(schoolId);
                            teacher.setBureauId(bureauId);
                            teacher.setClassIds(map.get("classStrs").toString());
                            Teacher t = teacherDao.getTeacherInSchool(teacher);
                            if (t == null) {
                                teacherDao.insert(teacher);
                            } else {
                                teacherDao.update(t);
                            }
                        }
                        if(parse.get("guardian") != null){
                            List<String> guardianDepts = parse.get("guardian");
                            if(roleType != 3){
                                roleType = 2;
                            }else{
                                //即为老师又为家长
                                roleType = 6;
                            }

                            Map<String, Object> map = getClassInfo(guardianDepts);
                            campusId = (Integer) map.get("campusId");

                            Parent parent = new Parent();
                            parent.setUserId(userId);
                            parent.setParentName(name);
                            parent.setCampusId(campusId);
                            parent.setClassId(map.get("classStrs").toString());
                            parent.setSchoolId(schoolId);
                            parent.setBureauId(bureauId);
                            Parent p = parentDao.getParentInSchool(parent);
                            if (p == null) {
                                parentDao.insert(parent);
                            } else {
                                parentDao.update(parent);
                            }
                        }
                    }

                    //身份从老师或家长变为管理员
                    if(syncAction.equals("user_leave_change") && roleType == 5){
                        List<User> userRole234 = userDao.getUserRole234(userId, corpId);
                        if(userRole234 != null && userRole234.size() > 0){
                            User user = userRole234.get(0);
                            userDao.deleteUserInCorp(user);
                        }
                    }

                    deptService.userSaveByRole(schoolId, corpId, campusId, apiUser, roleType, accessToken);

                }
            } else if (bizType == 14)
            {
                if (syncAction.equals("org_dept_remove")) {
                    syncBizDataMediumDao.updateStatus(id);
                    continue LOOP;
                }
                //tags家校通下的结构部门
                //增加年级和增加班级
                String deptId = ((Integer) bizDataParse.get("id")).toString();
                String tags = "";
                if(bizDataParse.get("tags") != null){
                    tags = bizDataParse.get("tags").toString();
                }
                Department dept = new Department();
                dept.setDeptId(deptId);
                dept.setCorpId(corpId);
                String deptName = (String) bizDataParse.get("name");
                dept.setName(deptName);
                dept.setParentId(((Integer) bizDataParse.get("parentid")).toString());
                if(tags.equals("class")){
                    GradeClass gc = new GradeClass();
                    gc.setDeptId(Long.parseLong(deptId));
                    gc.setClassName(deptName);
                    if(syncAction.equals("org_dept_modify")){
                        gradeClassDao.update(gc);
                    }else{
                        //getListParentDepts?accessToken=xxx&deptId=119466078
                        //response   "parentIds": [119466078,119528065,119543068,119435065,-7,1]
                        //获取年级，分校信息
                        OapiDepartmentListParentDeptsByDeptResponse response =
                                deptService.getListParentDeptsByDept(deptId, accessToken);
                        if(response == null){
                            continue LOOP;
                        }
                        List<Long> parentIds = response.getParentIds();
                        String gradeDept = parentIds.get(1).toString();
                        String periodDept = parentIds.get(2).toString();
                        Long campusDeptId = parentIds.get(3);
                        OapiDepartmentGetResponse gradeDeptDetail = deptService.getDeptDetail(gradeDept, accessToken);
                        OapiDepartmentGetResponse periodDeptDetail = deptService.getDeptDetail(periodDept, accessToken);
                        String gradeName = gradeDeptDetail.getName();
                        String periodName = periodDeptDetail.getName();
                        Campus campus = campusDao.getByDeptId(campusDeptId);
                        gc.setGradeName(gradeName);
                        gc.setSessionName(periodName);
                        gc.setCampusId(campus.getId());
                        gc.setSchoolId(schoolId);
                        gc.setBureauId(bureauId);

                        //获取老师，家长，学生部门id
                        OapiDepartmentListResponse deptList = deptService.getDeptList(deptId, accessToken, 0);
                        List<OapiDepartmentListResponse.Department> department = deptList.getDepartment();
                        for (OapiDepartmentListResponse.Department dept_1 : department) {
                            String name = dept_1.getName();
                            Long deptId_1 = dept_1.getId();
                            if(name.equals("学生")){
                                gc.setSDeptId(deptId_1);
                            }else if(name.equals("家长")){
                                gc.setPDeptId(deptId_1);
                            }else if(name.equals("老师")){
                                gc.setTDeptId(deptId_1);
                            }
                        }

                        gradeClassDao.insert(gc);
                    }
                }
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
                //学校信息的更新解决？ 学校名称修改
                //部门同步

                departmentDao.insert(dept);

            } else if (bizType == 16) {
                //企业同步

            }
            //更新已操作状态
            syncBizDataMediumDao.updateStatus(id);
        }
        return JsonResultUtil.success();
    }

    public Map<String, Object> getClassInfo(List<String> deptStrList) {
        String classStrs = "";
        Integer campusId = null;
        Map<String, Object> map = new HashMap<>();
        for (String deptId : deptStrList) {
                GradeClass byDeptId = gradeClassDao.getByDeptId(Long.parseLong(deptId));
                //班级信息已存在
                if(byDeptId != null){
                    campusId = byDeptId.getCampusId();
                    Integer classId = byDeptId.getId();
                    //之前的部门没有找到对应班级
                    if(!classStrs .equals("")){
                        classStrs = classStrs + "," + classId.toString();
                    }else{
                        classStrs = classId.toString();
                    }
                }else{
                    //班级信息还未录入
                    if(campusId.equals("")){
                        campusId = 0;
                    }
                }
        }
        map.put("classStrs", classStrs);
        map.put("campusId", campusId);
        return map;
    }


}
