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
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
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

    private int count = 0;

    @Override
    public void init(Integer schoolId) throws ApiException {
//        List<Integer> classIdList1 = gradeClassDao.getClassIdList(schoolId);
//        List<Integer> classIdList2 = new ArrayList<>();
        School school = schoolDao.getBySchoolId(schoolId);
        Integer bureauId = school.getBureauId();
        //todo
        //
        if (bureauId == null) {
            bureauId = -1;
        }
        String corpId = school.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
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
//                                classIdList2.add(classId);

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
////                    studentDao.deleteByClassId(cId);
////                    parentDao.deleteByClassId(cId);
//                }
//            }

            school.setState((short) 1);
            schoolDao.update(school);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //标记已初始化
    }

    @Override
    public void saveUserInClass(Long deptId) throws ApiException {
//        //教师表删除同步记录
//        Map<Integer, String> teacherClassIdMap1 = teacherDao.getTeacherClassIdsMap(classId);
//        Map<Integer, String> teacherClassIdMap2 = new HashMap<>();
//        //学生表删除同步记录
//        List<Integer> studentIdList1 = studentDao.getStudentIdListInClass(classId);
//        List<Integer> studentIdList2 = new ArrayList<>();
//        //家长表删除同步记录
//        List<Integer> parentIdList1 = parentDao.getParentIdListInClass(classId);
//        List<Integer> parentIdList2 = new ArrayList<>();
        GradeClass gc = gradeClassDao.getByDeptId(deptId);
        Integer classId = gc.getId();
        Integer schoolId = gc.getSchoolId();
        Integer bureauId = gc.getBureauId();
        School school = new School();
        school.setId(schoolId);
        String corpId = schoolDao.getBySchool(school).get(0).getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        int campusId = gc.getCampusId();
        Long classDeptId = gc.getDeptId();
        /*获取老师，学生，家长部门id*/
        OapiDepartmentListResponse resp4 = getDeptList(classDeptId.toString(), accessToken, 0);
        List<OapiDepartmentListResponse.Department> userDeptList = resp4.getDepartment();
        for (OapiDepartmentListResponse.Department dept4 : userDeptList) {
            String userName = "";
            String userId = "";
            int roleType = 0;
            String userRole = dept4.getName();
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
                if (userRole.equals("老师")) {
                    gc.setTDeptId(userDeptId);
                    for (OapiUserListbypageResponse.Userlist user : userList) {
                        userName = user.getName();
                        userId = user.getUserid();
//                        Integer teacherId = 0;
                        String classIds = "";
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
//                        classId = classIdStr;
                            teacher.setClassIds(classIdStr);
                            teacherDao.insert(teacher);
//                        teacherId = teacher.getId();
                        } else {
                            //判断老师所在班级是否存在
//                        teacherId = t.getId();
                            classIds = t.getClassIds();
                            String idsStr = "," + t.getClassIds() + ",";
                            if (!idsStr.contains("," + classIdStr + ",")) {
                                StringBuilder sb = new StringBuilder(classIds);
                                sb.append("," + classIdStr);
                                t.setClassIds(sb.toString());
                            }
                            teacherDao.update(t);
                        }

//                    teacherClassIdMap2.put(teacherId,classId);

                        userSaveByRole(schoolId, corpId, campusId, user, roleType, accessToken);

                    }
                } else if (userRole.equals("家长")) {
                    gc.setPDeptId(userDeptId);
                    for (OapiUserListbypageResponse.Userlist user : userList) {
                        userName = user.getName();
                        userId = user.getUserid();
//                    Integer parentId = 0;
                        roleType = 2;
                        String classIds = "";
                        String classIdStr = classId + "";
                        Parent parent = new Parent();
                        parent.setUserId(userId);
                        parent.setParentName(userName);
                        parent.setCampusId(campusId);
                        parent.setSchoolId(schoolId);
                        parent.setBureauId(bureauId);
                        Parent p = parentDao.getByUserId(userId);
                        if (p == null) {
                            parent.setClassId(classIdStr);
                            parentDao.insert(parent);
//                        parentId = parent.getId();
                        } else {
//                        parentId = p.getId();
                            classIds = p.getClassId();
                            String idsStr = "," + p.getClassId() + ",";
                            if (!idsStr.contains("," + classIdStr + ",")) {
                                StringBuilder sb = new StringBuilder(classIds);
                                sb.append("," + classIdStr);
                                parent.setClassId(sb.toString());
                            }
                            parentDao.update(parent);
                        }
//                    parentIdList2.add(parentId);

                        userSaveByRole(schoolId, corpId, campusId, user, roleType, accessToken);

                    }
                } else if (userRole.equals("学生")) {
                    gc.setSDeptId(userDeptId);
                    for (OapiUserListbypageResponse.Userlist user : userList) {
                        userName = user.getName();
                        userId = user.getUserid();
//                    Integer studentId = 0;
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
//                        studentId = student.getId();
                        } else {
//                        studentId = s.getId();
                            studentDao.update(student);
                        }
//                    studentIdList2.add(studentId);
                        userSaveByRole(schoolId, corpId, campusId, user, roleType, accessToken);
                    }
                }
            }
        }

        //更新班级下老师，家长，学生的部门id
        gradeClassDao.update(gc);
//        //学生表删除数据同步
//        if(studentIdList1 != null && studentIdList1.size() > 0){
//            List<Integer> studentIdList = CommonUtils.removeIntegerDupsInList(studentIdList1, studentIdList2);
//            for (Integer id : studentIdList) {
//                studentDao.delete(id);
//            }
//        }
//        //家长表删除数据同步
//        if(parentIdList1 != null && parentIdList1.size() > 0){
//            List<Integer> parentIdList = CommonUtils.removeIntegerDupsInList(parentIdList1, parentIdList2);
//            for (Integer id : parentIdList) {
//                parentDao.delete(id);
//            }
//        }

//        //老师表删除数据同步
//        if(teacherClassIdMap1 != null && teacherClassIdMap2.size() > 0){
//            Set<Integer> strings1 = teacherClassIdMap1.keySet();
//            String[] idStrArray1 =  (String[])strings1.toArray();
//            Set<Integer> strings2 = teacherClassIdMap2.keySet();
//            String[] idStrArray2 =  (String[])strings2.toArray();
//            String[] classIdStrArr1 = CommonUtils.removeStringDups(idStrArray1, idStrArray2);
//            for (String s : classIdStrArr1) {
//                teacherDao.delete(Integer.parseInt(s));
//            }
//            String[] classIdStrArr2 = CommonUtils.retainStringDups(idStrArray1, idStrArray2);
//            for (String s : classIdStrArr2) {
//                int id = Integer.parseInt(s);
//                String[] classIdStrs1 = teacherClassIdMap1.get(id).split(",");
//                String[] classIdStrs2 = teacherClassIdMap2.get(id).split(",");
//                String[] classIdStrs3 = CommonUtils.retainStringDups(classIdStrs1, classIdStrs2);
//                Teacher teacher = new Teacher();
//                teacher.setId(id);
//                teacher.setClassId(Arrays.toString(classIdStrs3));
//                teacherDao.updateRole5ToOtherRole(teacher);
//            }

//        }
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
        String userId = user.getUserid();
        Boolean isAdmin = user.getIsAdmin();
        if (isAdmin != null && isAdmin == true) {
            Administrator a1 = new Administrator();
            //todo
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
                    //todo
                    //处理家长多个classId返回
                    List<Parent> parentsInSchool = parentDao.getParentsInSchool(parent);
                    if (parentsInSchool == null || parentsInSchool.size() == 0) {
                        continue;
                    }
                    Parent p = parentsInSchool.get(0);
                    String classIds = p.getClassId();
                    map.put("classIds", classIds);
                    p.setAvatar(avatar);
                    parentDao.update(p);
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
    public OapiUserGetOrgUserCountResponse getOrgUserCount(String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_org_user_count");
        OapiUserGetOrgUserCountRequest request = new OapiUserGetOrgUserCountRequest();
        request.setOnlyActive(1L);
        request.setHttpMethod("GET");
        OapiUserGetOrgUserCountResponse response = client.execute(request, accessToken);
        return response;
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
