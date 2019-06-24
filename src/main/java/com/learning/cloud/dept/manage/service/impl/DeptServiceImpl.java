package com.learning.cloud.dept.manage.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.dept.campus.dao.CampusDao;
import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.index.dao.AuthAppInfoDao;
import com.learning.cloud.index.dao.AuthCorpInfoDao;
import com.learning.cloud.index.entity.AuthCorpInfo;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeptServiceImpl implements DeptService {

    @Autowired
    private AuthCorpInfoDao authCorpInfoDao;

    @Autowired
    private AuthAppInfoDao authAppInfoDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private BureauDao bureaudao;

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

    /*初始化学校下部门信息*/
    @Override
    public ServiceResult initDepartment() {
        /*初始化教育局和学校表数据*/
        List<AuthCorpInfo> corpInfos = authCorpInfoDao.getCorpInfos();
        for (AuthCorpInfo corpInfo : corpInfos) {
            String corpName = corpInfo.getCorpName();
            if(corpName.endsWith("学校")){
                School school = new School();
                school.setSchoolName(corpName);
                List<School> bySchool = schoolDao.getBySchool(school);
                if(bySchool == null || bySchool.size() == 0){
                    schoolDao.insert(school);
                    //填充表
                    init(school);
                }
            }else{
                Bureau byBureauName = bureaudao.getByBureauName(corpName);
                if(byBureauName == null){
                    Bureau bureau = new Bureau();
                    bureau.setBureauName(corpName);
                    bureaudao.insert(bureau);
                }
            }
        }

        return ServiceResult.success("ok");
    }

    private void init(School school) {
        String corpId = schoolDao.getCorpIdBySchoolName(school.getSchoolName());
        String corpAccessToken = authAppInfoDao.getCorpAccessToken(corpId);
        try {
            OapiDepartmentListResponse resp = getDeptList("1", corpAccessToken , 1);
            List<OapiDepartmentListResponse.Department> departmentList = resp.getDepartment();
            int i = 0;
            /*校区表初始化*/
            for (OapiDepartmentListResponse.Department dept : departmentList) {
                Long parentDeptId = dept.getParentid();
                if(parentDeptId == -7){
                    int campusId;
                    Campus campus = new Campus();
                    campus.setCampusName(dept.getName());
                    campus.setSchoolId(school.getId());
                    Campus c = campusDao.getCampus(campus);
                    if(c == null){
                        campusDao.insert(campus);
                        campusId = campus.getId();
                    }else{
                        campusId = c.getId();
                    }
                    i++;
                    /*班级表填充*/
                    Long campusDeptId = dept.getId();
                    /*得到session_name字段*/
                    OapiDepartmentListResponse resp1 = getDeptList(campusDeptId.toString(), corpAccessToken, 0);
                    List<OapiDepartmentListResponse.Department> sessionDeptList = resp1.getDepartment();
                    for (OapiDepartmentListResponse.Department dept1 : sessionDeptList) {
                        String sessionName = dept1.getName();
                        Long sessionDeptId = dept1.getId();
                        /*grade_name字段*/
                        OapiDepartmentListResponse resp2 = getDeptList(sessionDeptId.toString(), corpAccessToken, 0);
                        List<OapiDepartmentListResponse.Department> gradeDeptList = resp2.getDepartment();
                        for (OapiDepartmentListResponse.Department dept2 : gradeDeptList) {
                            String gradeName = dept2.getName();
                            Long gradeDeptId = dept2.getId();
                            /*class_name字段*/
                            OapiDepartmentListResponse resp3 = getDeptList(gradeDeptId.toString(), corpAccessToken, 0);
                            List<OapiDepartmentListResponse.Department> classDeptList = resp3.getDepartment();
                            for (OapiDepartmentListResponse.Department dept3 : classDeptList) {
                                String className = dept3.getName();
                                Long classDeptId = dept3.getId();
                                /*grade_class表的更新*/
                                /**/
                                int classId;
                                GradeClass gradeClass = new GradeClass();
                                gradeClass.setCampusId(campusId);
                                gradeClass.setSessionName(sessionName);
                                gradeClass.setGradeName(gradeName);
                                gradeClass.setClassName(className);
                                GradeClass gc = gradeClassDao.getByGradeClass(gradeClass);
                                if(gc == null){
                                    gradeClassDao.insert(gradeClass);
                                    classId = gradeClass.getId();
                                }else{
                                    classId = gc.getId();
                                }
                                /*获取老师，学生，家长部门id*/
                                OapiDepartmentListResponse resp4 = getDeptList(classDeptId.toString(), corpAccessToken, 0);
                                List<OapiDepartmentListResponse.Department> userDeptList = resp4.getDepartment();
                                for (OapiDepartmentListResponse.Department dept4 : userDeptList) {
                                    String userRole = dept4.getName();
                                    Long id = dept4.getId();
                                    /*用户表更新*/
                                    OapiUserSimplelistResponse resp5 = getDeptUserList(id.toString(), corpAccessToken);
                                    List<OapiUserSimplelistResponse.Userlist> userlist = resp5.getUserlist();
                                    /*用户表填充*/
                                    if(userRole.equals("老师")){
                                        for (OapiUserSimplelistResponse.Userlist user : userlist) {
                                            String userName = user.getName();
                                            String userid = user.getUserid();
                                            String classIdStr = classId + "";
                                            Teacher teacher = new Teacher();
                                            teacher.setTeacherName(userName);
                                            teacher.setUserId(userid);
                                            teacher.setCampusId(campusId);
                                            Teacher t = teacherDao.getByUserId(userid);
                                            if(t == null){
                                                teacher.setClassIds(classIdStr);
                                                teacherDao.insert(teacher);
                                            }else{
                                                String classIds = t.getClassIds();
                                                String idsStr = "," + t.getClassIds() + ",";
                                                if(!idsStr.contains("," + classIdStr + ",")){
                                                    StringBuilder sb = new StringBuilder(classIds);
                                                    sb.append("," + classIdStr);
                                                    t.setClassIds(sb.toString());
                                                    teacherDao.update(t);
                                                }
                                            }
                                        }
                                    }else if(userRole.equals("家长")){
                                        for (OapiUserSimplelistResponse.Userlist user : userlist) {
                                            String userName = user.getName();
                                            String userid = user.getUserid();
                                            Parent parent = new Parent();
                                            parent.setUserId(userid);
                                            parent.setParentName(userName);
                                            parent.setClassId(classId);
                                            Parent p = parentDao.getByUserId(userid);
                                            if(p == null){
                                                parentDao.insert(parent);
                                            }else{
                                                parentDao.update(parent);
                                            }
                                        }
                                    }else if(userRole.equals("学生")){
                                        for (OapiUserSimplelistResponse.Userlist user : userlist) {
                                            String userName = user.getName();
                                            String userid = user.getUserid();
                                            Student student = new Student();
                                            student.setUserId(userid);
                                            student.setStudentName(userName);
                                            student.setClassId(classId);
                                            student.setCampusId(campusId);
                                            Student s = studentDao.getByUserId(student);
                                            if(s == null){
                                                studentDao.insert(student);
                                            }else{
                                                studentDao.update(student);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            /*多校区则更新对应标识*/
            if(i > 1){
                campusDao.updateCampusType(school.getId());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        school.setState((short)1);
    }

    /*获取子部门的id列表*/
    @Override
    public OapiDepartmentListIdsResponse getDeptListIds(String pDeptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId(pDeptId);
        request.setHttpMethod("GET");
        OapiDepartmentListIdsResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取部门列表*/
    @Override
    public OapiDepartmentListResponse getDeptList(String pDeptId,String accessToken,Integer isFetchChild ) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(pDeptId);
        if(isFetchChild == 0){
            request.setFetchChild(false);
        }else{
            request.setFetchChild(true);
        }
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取部门详情*/
    @Override
    public OapiDepartmentGetResponse getDeptDetail(String deptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取部门用户userid列表*/
    @Override
    public OapiUserGetDeptMemberResponse getDeptMember(String deptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
        req.setDeptId(deptId);
        req.setHttpMethod("GET");
        OapiUserGetDeptMemberResponse rsp = client.execute(req, accessToken);
        return rsp;
    }

    /*获取部门用户*/
    @Override
    public OapiUserSimplelistResponse getDeptUserList(String deptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(Long.parseLong(deptId));
        request.setOffset(0L);
        request.setSize(10L);
        request.setHttpMethod("GET");
        OapiUserSimplelistResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取用户详情*/
    @Override
    public OapiUserGetResponse getUserDetail(String userId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取部门用户详情*/
    @Override
    public OapiUserListbypageResponse getDeptUserListByPage(long deptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserListbypageRequest request = new OapiUserListbypageRequest();
        request.setDepartmentId(deptId);
        request.setOffset(0L);
        request.setSize(10L);
        request.setOrder("entry_desc");
        request.setHttpMethod("GET");
        OapiUserListbypageResponse execute = client.execute(request,accessToken);
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

    /*获取所有的角色列表*/
    public OapiRoleListResponse getRoleList(String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/list");
        OapiRoleListRequest request = new OapiRoleListRequest();
        request.setOffset(0L);
        request.setSize(10L);

        OapiRoleListResponse response = client.execute(request, accessToken);
        return response;
    }
}
