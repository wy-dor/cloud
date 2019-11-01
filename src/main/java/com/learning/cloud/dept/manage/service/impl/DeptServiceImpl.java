package com.learning.cloud.dept.manage.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.learning.cloud.course.dao.CourseDao;
import com.learning.cloud.course.entity.Course;
import com.learning.cloud.dept.campus.dao.CampusDao;
import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.admin.dao.AdministratorDao;
import com.learning.cloud.user.admin.entity.Administrator;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.parent.service.ParentService;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.user.teacher.service.TeacherService;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.utils.CommonUtils;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private CampusDao campusDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AdministratorDao administratorDao;

    @Autowired
    private ParentService parentService;

    @Autowired
    private TeacherService teacherService;

    private int count = 0;

    @Override
    public void init(Integer schoolId) throws ApiException {
//        List<Integer> classIdList1 = gradeClassDao.getClassIdList(schoolId);
//        List<Integer> classIdList2 = new ArrayList<>();
        School school = schoolDao.getBySchoolId(schoolId);

        Integer bureauId = school.getBureauId();
        if (bureauId == null) {
            bureauId = -1;
        }
        String corpId = school.getCorpId();
        String accessToken = null;
        try {
            accessToken = authenService.getAccessToken(corpId);
            //保存用户人数
            Long orgUserCount = getOrgUserCount(accessToken, 0L);
            Long orgActiveUserCount = getOrgUserCount(accessToken, 1L);
            school.setOrgUserCount(orgUserCount);
            school.setOrgActiveUserCount(orgActiveUserCount);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        try {
            OapiDepartmentListResponse resp = getDeptList("", accessToken, 0);
            List<OapiDepartmentListResponse.Department> departmentList_0 = resp.getDepartment();
            int i = 0;
            /*校区表初始化*/
            for (OapiDepartmentListResponse.Department dept_0 : departmentList_0) {
                Long deptId_0 = dept_0.getId();
                if (deptId_0 == -7) {
                    OapiDepartmentListResponse resp_1 = getDeptList("-7", accessToken, 0);
                    List<OapiDepartmentListResponse.Department> departmentList_1 = resp_1.getDepartment();
                    for (OapiDepartmentListResponse.Department dept_1 : departmentList_1) {
                        Long campusDeptId = dept_1.getId();
                        int campusId;
                        Campus campus = new Campus();
                        campus.setDeptId(campusDeptId);
                        Campus c = campusDao.getByCampus(campus);
                        campus.setSchoolId(schoolId);
                        campus.setCampusName(dept_1.getName());
                        if (c == null) {
                            campusDao.insert(campus);
                            campusId = campus.getId();
                        } else {
                            campusId = c.getId();
                            campus.setId(campusId);
                            campusDao.update(campus);
                        }
                        i++;
                        /*班级表填充*/
                        /*得到session_name字段*/
                        OapiDepartmentListResponse resp1 = getDeptList(campusDeptId.toString(), accessToken, 0);
                        List<OapiDepartmentListResponse.Department> sessionDeptList = resp1.getDepartment();
                        for (OapiDepartmentListResponse.Department dept1 : sessionDeptList) {
                            String sessionName = dept1.getName();
                            Long sessionDeptId = dept1.getId();
                            /*grade_name字段*/
                            OapiDepartmentListResponse resp2 = getDeptList(sessionDeptId.toString(), accessToken, 0);
                            List<OapiDepartmentListResponse.Department> gradeDeptList = resp2.getDepartment();
                            for (OapiDepartmentListResponse.Department dept2 : gradeDeptList) {
                                String gradeName = dept2.getName();
                                Long gradeDeptId = dept2.getId();
                                /*class_name字段*/
                                OapiDepartmentListResponse resp3 = getDeptList(gradeDeptId.toString(), accessToken, 0);
                                List<OapiDepartmentListResponse.Department> classDeptList = resp3.getDepartment();
                                for (OapiDepartmentListResponse.Department dept3 : classDeptList) {
                                    String className = dept3.getName();
                                    Long classDeptId = dept3.getId();
                                    /*grade_class表的更新*/
                                    Integer classId;
                                    GradeClass gradeClass = new GradeClass();
                                    gradeClass.setDeptId(classDeptId);
                                    List<GradeClass> classList = gradeClassDao.getByGradeClass(gradeClass);
                                    gradeClass.setCampusId(campusId);
                                    gradeClass.setSessionName(sessionName);
                                    gradeClass.setGradeName(gradeName);
                                    gradeClass.setClassName(className);
                                    gradeClass.setSchoolId(schoolId);
                                    gradeClass.setBureauId(bureauId);
                                    if (classList == null || classList.size() == 0) {
                                        gradeClassDao.insert(gradeClass);
                                        classId = gradeClass.getId();
                                        //增加课程
                                        Course course = new Course();
                                        course.setSchoolId(schoolId.longValue());
                                        course.setClassId(classId.longValue());
                                        course.setGradeName(gradeName);
                                        course.setClassName(className);
                                        courseDao.addCourse(course);
                                    } else {
                                        GradeClass gc = classList.get(0);
                                        classId = gc.getId();
                                        gradeClass.setId(classId);
                                        gradeClassDao.update(gradeClass);
                                    }
//                                    classIdList2.add(classId);

                                    /*班级结构数据同步*/
                                    saveUserInClass(classDeptId);
                                }
                            }
                        }
                    }
                } else {
                    recurseGetUser(deptId_0, accessToken, corpId, schoolId);
                }
            }

            /*多校区则更新对应标识*/
            if (i > 1) {
                campusDao.updateCampusType(schoolId);
            }

            //添加不在部门里的其他身份的用户信息
            Boolean flag = true;
            Long offset = 0L;
            while (flag) {
                OapiUserListbypageResponse response_1 = getDeptUserListByPage(1L, offset, accessToken);
                List<OapiUserListbypageResponse.Userlist> userList = response_1.getUserlist();
                if (userList.size() < 100) {
                    flag = false;
                } else {
                    offset += 1;
                }
                for (OapiUserListbypageResponse.Userlist user : userList) {
                    userSaveByRole(schoolId, corpId, null, user, 5, accessToken);
                }
            }

            //删除班级记录同步
//            List<Integer> classIdList3 = CommonUtils.removeIntegerDupsInList(classIdList1, classIdList2);
//            if(classIdList3 != null && classIdList3.size() > 0){
//                for (Integer cId : classIdList3) {
//                    gradeClassDao.delete(cId);
//                    studentDao.deleteByClassId(cId);
//                    parentService.removeParentsInClass(cId);
//                    teacherService.removeTeachersInClass(cId);
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //标记已初始化
        school.setState((short) 1);
        schoolDao.update(school);
    }

    @Override
    public void saveUserInClass(Long deptId) throws ApiException {

        GradeClass gc = gradeClassDao.getByDeptId(deptId);
        Integer classId = gc.getId();

        //班级基本信息获取
        Integer schoolId = gc.getSchoolId();
        Integer bureauId = gc.getBureauId();
        School school = new School();
        school.setId(schoolId);
        String corpId = schoolDao.getBySchool(school).get(0).getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        int campusId = gc.getCampusId();
        Long classDeptId = gc.getDeptId();

        //删除同步
        //教师表删除同步记录
        List<String> teacherUserIdList1 = teacherDao.listTeacherUserIdInClass(classId);
        List<String> teacherUserIdList2 = new ArrayList<>();
        //学生表删除同步记录
        List<String> studentUserIdList1 = studentDao.listStudentUserIdInClass(classId);
        List<String> studentUserIdList2 = new ArrayList<>();
        //家长表删除同步记录
        List<String> parentUserIdList1 = parentDao.listParentUserIdInClass(classId);
        List<String> parentUserIdList2 = new ArrayList<>();

        /*获取老师，学生，家长部门id*/
        OapiDepartmentListResponse resp4 = getDeptList(classDeptId.toString(), accessToken, 0);
        //以部门为单位
        List<OapiDepartmentListResponse.Department> userDeptList = resp4.getDepartment();
        for (OapiDepartmentListResponse.Department dept4 : userDeptList) {
            //部门名称
            String dept4Name = dept4.getName();
            String userName = "";
            String userId = "";
            int roleType = 5;
            Long userDeptId = dept4.getId();
            /*用户表更新*/
            //一个部门中若存在100以上的人员人数则需要再次进行请求
            Boolean flag = true;
            Long offset = 0L;
            while (flag) {
                OapiUserListbypageResponse resp5 = getDeptUserListByPage(userDeptId, offset, accessToken);
                List<OapiUserListbypageResponse.Userlist> userList = resp5.getUserlist();
                if (userList.size() < 100) {
                    flag = false;
                } else {
                    offset += 1;
                }
                /*用户表填充*/
                if (dept4Name.equals("老师")) {
                    gc.setTDeptId(userDeptId);
                    for (OapiUserListbypageResponse.Userlist user : userList) {
                        userName = user.getName();
                        userId = user.getUserid();
                        //用于对比删除
                        teacherUserIdList2.add(userId);
                        roleType = 3;
                        String classIdStr = classId + "";
                        Teacher teacher = new Teacher();
                        teacher.setTeacherName(userName);
                        teacher.setUserId(userId);
                        teacher.setCampusId(campusId);
                        teacher.setSchoolId(schoolId);
                        teacher.setBureauId(bureauId);
                        Teacher t = teacherDao.getTeacherInSchool(teacher);
                        if (t == null) {
                            teacher.setClassIds(classIdStr);
                            teacherDao.insert(teacher);
                        } else {
                            //判断老师所在班级是否存在
                            String classIds = t.getClassIds();
                            String idsStr = "," + t.getClassIds() + ",";
                            if (!idsStr.contains("," + classIdStr + ",")) {
                                StringBuilder sb = new StringBuilder(classIds);
                                sb.append("," + classIdStr);
                                t.setClassIds(sb.toString());
                            }
                            teacherDao.update(t);
                        }

                        userSaveByRole(schoolId, corpId, campusId, user, roleType, accessToken);

                    }
                } else if (dept4Name.equals("家长")) {
                    gc.setPDeptId(userDeptId);
                    for (OapiUserListbypageResponse.Userlist user : userList) {
                        userName = user.getName();
                        userId = user.getUserid();
                        //用于对比删除
                        parentUserIdList2.add(userId);
                        roleType = 2;
                        String classIdStr = classId + "";
                        Parent parent = new Parent();
                        parent.setUserId(userId);
                        parent.setParentName(userName);
                        parent.setCampusId(campusId);
                        parent.setSchoolId(schoolId);
                        parent.setBureauId(bureauId);
                        Parent p = parentDao.getParentInSchool(parent);
                        if (p == null) {
                            parent.setClassId(classIdStr);
                            parentDao.insert(parent);
                        } else {
                            String classIds = p.getClassId();
                            String idsStr = "," + p.getClassId() + ",";
                            if (!idsStr.contains("," + classIdStr + ",")) {
                                StringBuilder sb = new StringBuilder(classIds);
                                sb.append("," + classIdStr);
                                parent.setClassId(sb.toString());
                            }
                            parentDao.update(parent);
                        }

                        userSaveByRole(schoolId, corpId, campusId, user, roleType, accessToken);

                    }
                } else if (dept4Name.equals("学生")) {
                    gc.setSDeptId(userDeptId);
                    for (OapiUserListbypageResponse.Userlist user : userList) {
                        userName = user.getName();
                        userId = user.getUserid();
                        //用于对比删除
                        studentUserIdList2.add(userId);
                        roleType = 4;
                        Student student = new Student();
                        student.setUserId(userId);
                        student.setStudentName(userName);
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
                        userSaveByRole(schoolId, corpId, campusId, user, roleType, accessToken);
                    }
                }
            }
        }

        //更新班级下老师，家长，学生的部门id
        gradeClassDao.update(gc);

        //学生表删除数据同步
        if (studentUserIdList1 != null && studentUserIdList1.size() > 0){
            List<String> studentUserIdList3 = CommonUtils.removeStringDupsInList(studentUserIdList1, studentUserIdList2);
            for (String userId : studentUserIdList3) {
                studentDao.deleteByUserId(userId);
            }
        }

        if (parentUserIdList1 != null && parentUserIdList1.size() > 0){
            List<String> parentUserIdList3 = CommonUtils.removeStringDupsInList(parentUserIdList1, parentUserIdList2);
            for (String userId : parentUserIdList3) {
                Parent parent = new Parent();
                parent.setUserId(userId);
                parent.setSchoolId(schoolId);
                Parent p = parentDao.getParentInSchool(parent);
                parentService.removeParentInClass(classId, p);
            }
        }

        if (teacherUserIdList1 != null && teacherUserIdList1.size() > 0){
            List<String> teacherUserIdList3 = CommonUtils.removeStringDupsInList(teacherUserIdList1, teacherUserIdList2);
            for (String userId : teacherUserIdList3) {
                Teacher teacher = new Teacher();
                teacher.setSchoolId(schoolId);
                teacher.setUserId(userId);
                Teacher t = teacherDao.getTeacherInSchool(teacher);
                teacherService.removeTeacherInClass(classId,t);
            }
        }

    }


    @Override
    public void recurseGetUser(Long departmentId, String accessToken, String corpId, Integer schoolId) {
        OapiDepartmentListResponse resp = null;
        try {
            resp = getDeptList(departmentId.toString(), accessToken, 0);
        } catch (ApiException e) {
            return;
        }
        List<OapiDepartmentListResponse.Department> departmentList = resp.getDepartment();
        if (departmentList != null && departmentList.size() > 0) {
            for (OapiDepartmentListResponse.Department department : departmentList) {
                Long deptId = department.getId();
                recurseGetUser(deptId, accessToken, corpId, schoolId);
            }
        } else {
            //一个部门中若存在100以上的人员人数则需要再次进行请求
            Boolean flag = true;
            Long offset = 0L;
            while (flag) {
                OapiUserListbypageResponse resp5 = null;
                try {
                    resp5 = getDeptUserListByPage(departmentId, offset, accessToken);
                } catch (ApiException e) {
                    return;
                }
                List<OapiUserListbypageResponse.Userlist> userList = resp5.getUserlist();
                if (userList.size() < 100) {
                    flag = false;
                } else {
                    offset += 1;
                }
                for (OapiUserListbypageResponse.Userlist user : userList) {
                    userSaveByRole(schoolId, corpId, null, user, 5, accessToken);
                }
            }
        }
    }

    //对用户角色进行判断存储
    @Override
    public void userSaveByRole(Integer schoolId, String corpId, Integer campusId, OapiUserListbypageResponse.Userlist user,
                               int roleType, String accessToken){
        if(roleType == 4){
            return;
        }
        String userId = user.getUserid();
        Boolean isAdmin = user.getIsAdmin();
        if (isAdmin != null && isAdmin == true) {
            Administrator a1 = new Administrator();
            //判断获取的值是否为空，为空则不插入
            String userDetailName = user.getName();
            //管理员表处理
            if (userDetailName != null) {
                a1.setName(userDetailName);
                a1.setUserId(userId);
                a1.setSchoolId(schoolId);
                Administrator byAdm = administratorDao.getByAdm(a1);
                if (byAdm == null) {
                    administratorDao.insert(a1);
                } else {
                    administratorDao.updateName(a1);
                }
            }
        }


        //其他身份不覆盖主要角色
        if (roleType == 5) {
            List<User> userRole234 = userDao.getUserRole234(userId, corpId);
            if (userRole234.size() > 0) {
                return;
            }
        }

        User bySchoolRoleIdentity_5 = null;
        User u = new User();
        u.setUserId(userId);
        u.setSchoolId(schoolId);

        String userDetailUnionid = user.getUnionid();
        if (userDetailUnionid == null) {
            return;
        }
        u.setUnionId(userDetailUnionid);
        u.setUserName(user.getName());
        u.setUserId(userId);
        u.setCampusId(campusId);
        u.setCorpId(corpId);
        u.setAvatar(user.getAvatar());
        Boolean active = user.getActive();
        if (active != null) {
            if (active) {
                u.setActive((short) 1);
            } else {
                u.setActive((short) 0);
            }
        }

        u.setRoleType(5);
        bySchoolRoleIdentity_5 = userDao.getBySchoolRoleIdentity(u);

        if (roleType == 5) {
            //若为其他身份按照常规操作
            if (bySchoolRoleIdentity_5 == null) {
                userDao.insert(u);
            } else {
                userDao.updateWithSpecificRole(u);
            }
        } else {
            //若为通讯录下角色则且无其他身份则常规保存
            u.setRoleType(roleType);
            if (bySchoolRoleIdentity_5 == null) {
                User bySchoolRoleIdentity_s = userDao.getBySchoolRoleIdentity(u);
                if (bySchoolRoleIdentity_s == null) {
                    userDao.insert(u);
                } else {
                    userDao.updateWithSpecificRole(u);
                }
            } else {
                //若为通讯录下角色还有其他身份则取消其他身份
                userDao.updateRole5ToOtherRole(u);
            }
        }

    }

    @Override
    public Map<String, String> getUserRole(String userId, String accessToken, String avatar, String corpId) throws ApiException {
        Map<String, String> map = new HashMap<>();
        String roleName = "";
        Integer schoolId = schoolDao.getSchoolByCorpId(corpId).getId();
        OapiDepartmentListParentDeptsResponse resp = getListParentDeptsByUser(userId, accessToken);
        String department = resp.getDepartment();
        /*"department": "[[117451249, 117656244, 117680160, 117597295, 117425251, -7, 1]]"*/

        if (department.equals("[]")) {
            Student byUserId = studentDao.getByUserId(userId);
            if (byUserId == null) {
                map.put("roleName", "无");
            } else {
                Integer classId = byUserId.getClassId();
                map.put("roleName", "学生");
                map.put("classId", classId + "");
                byUserId.setAvatar(avatar);
                studentDao.update(byUserId);
            }
        } else {
            /*"department": "[[117451247, 117656244, 117680160, 117597295, 117425251, -7, 1],
            [118996286, 118754319, 118798287, 118917275, 118958294, -7, 1]]"*/
            String[] split = department.replace("[", "").split("], ");
            for (String s : split) {
                String deptId = s.split(",")[0];
                OapiDepartmentGetResponse resp1 = getDeptDetail(deptId, accessToken);
                String deptName = resp1.getName();
                if (deptName.equals("老师")) {
                    roleName = "老师";
                    Teacher teacher = new Teacher();
                    teacher.setUserId(userId);
                    teacher.setSchoolId(schoolId);
                    Teacher t = teacherDao.getTeacherInSchool(teacher);
                    if (t == null) {
                        continue;
                    }
                    Integer id = t.getId();
                    map.put("teacherId", id + "");
                    t.setAvatar(avatar);
                    teacherDao.update(t);
                    break;
                } else if (deptName.equals("家长")) {
                    roleName = "家长";
                    Parent parent = new Parent();
                    parent.setUserId(userId);
                    parent.setSchoolId(schoolId);
                    Parent parentInSchool = parentDao.getParentInSchool(parent);
                    if (parent == null ) {
                        continue;
                    }
                    String classIds = parentInSchool.getClassId();
                    map.put("classIds", classIds);
                    parentInSchool.setAvatar(avatar);
                    parentDao.update(parentInSchool);
                    break;
                }
            }
            map.put("roleName", roleName);
        }
        return map;
    }

    /*获取子部门的id列表*/
    @Override
    public OapiDepartmentListIdsResponse getDeptListIds(String pDeptId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId(pDeptId);
        request.setHttpMethod("GET");
        OapiDepartmentListIdsResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取部门列表*/
    @Override
    public OapiDepartmentListResponse getDeptList(String pDeptId, String accessToken, Integer isFetchChild) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(pDeptId);
        if (isFetchChild == 0) {
            request.setFetchChild(false);
        } else {
            request.setFetchChild(true);
        }
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取部门详情*/
    @Override
    public OapiDepartmentGetResponse getDeptDetail(String deptId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取部门用户userId列表*/
    @Override
    public OapiUserGetDeptMemberResponse getDeptMember(String deptId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
        req.setDeptId(deptId);
        req.setHttpMethod("GET");
        OapiUserGetDeptMemberResponse rsp = client.execute(req, accessToken);
        return rsp;
    }

    /*获取部门用户*/
    @Override
    public OapiUserSimplelistResponse getDeptUserList(String deptId, Long offset, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(Long.parseLong(deptId));
        request.setOffset(offset);
        request.setSize(100L);
        request.setHttpMethod("GET");
        OapiUserSimplelistResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取用户详情*/
    @Override
    public OapiUserGetResponse getUserDetail(String userId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取部门用户详情*/
    @Override
    public OapiUserListbypageResponse getDeptUserListByPage(Long deptId, Long offset, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserListbypageRequest request = new OapiUserListbypageRequest();
        request.setDepartmentId(deptId);
        request.setOffset(offset);
        request.setSize(100L);
        request.setOrder("entry_desc");
        request.setHttpMethod("GET");
        OapiUserListbypageResponse execute = client.execute(request, accessToken);
        return execute;
    }

    /*查询部门的所有上级父部门路径*/
    @Override
    public OapiDepartmentListParentDeptsByDeptResponse getListParentDeptsByDept(String deptId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts_by_dept");
        OapiDepartmentListParentDeptsByDeptRequest request = new OapiDepartmentListParentDeptsByDeptRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentListParentDeptsByDeptResponse response = client.execute(request, accessToken);
        return response;
    }

    /*查询指定用户的所有上级父部门路径*/
    @Override
    public OapiDepartmentListParentDeptsResponse getListParentDeptsByUser(String userId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts");
        OapiDepartmentListParentDeptsRequest request = new OapiDepartmentListParentDeptsRequest();
        request.setUserId(userId);
        request.setHttpMethod("GET");
        OapiDepartmentListParentDeptsResponse response = client.execute(request, accessToken);
        return response;
    }

    /*查询企业下的管理员列表*/
    @Override
    public List<Administrator> getURLAdmin(String accessToken) throws ApiException {
        List<Administrator> administratorList = new ArrayList<>();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_admin");
        OapiUserGetAdminRequest request = new OapiUserGetAdminRequest();
        request.setHttpMethod("GET");
        OapiUserGetAdminResponse response = client.execute(request, accessToken);
        List<OapiUserGetAdminResponse.AdminList> adminList = response.getAdminList();
        for (OapiUserGetAdminResponse.AdminList admin : adminList) {
            Administrator a = new Administrator();
            String userId = admin.getUserid();
            a.setUserId(userId);
            a.setSysLevel(admin.getSysLevel());
            OapiUserGetResponse userDetail = getUserDetail(userId, accessToken);
            a.setName(userDetail.getName());
            administratorList.add(a);
        }
        return administratorList;
    }

    /*查询企业员工人数*/
    @Override
    public Long getOrgUserCount(String accessToken, Long onlyActive) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_org_user_count");
        OapiUserGetOrgUserCountRequest request = new OapiUserGetOrgUserCountRequest();
        request.setOnlyActive(onlyActive);
        request.setHttpMethod("GET");
        OapiUserGetOrgUserCountResponse response = client.execute(request, accessToken);
        Long count = response.getCount();
        return count;
    }

    @Override
    public int recurseGetUserCount(String departmentId, String accessToken) throws ApiException {
        OapiDepartmentListResponse resp = getDeptList(departmentId, accessToken, 0);
        List<OapiDepartmentListResponse.Department> departmentList = resp.getDepartment();
        if (departmentList != null && departmentList.size() > 0) {
            for (OapiDepartmentListResponse.Department department : departmentList) {
                Long deptId = department.getId();
                if (deptId != -7) {
                    recurseGetUserCount(deptId.toString(), accessToken);
                }
            }
        } else {
            OapiUserSimplelistResponse deptUserListResponse = getDeptUserList(departmentId, 0L, accessToken);
            List<OapiUserSimplelistResponse.Userlist> userListInfo = deptUserListResponse.getUserlist();
            if (userListInfo.size() > 0) {
                count += userListInfo.size();
            }
        }
        return count;
    }

}
