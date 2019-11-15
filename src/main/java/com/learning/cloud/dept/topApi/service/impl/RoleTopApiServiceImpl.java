package com.learning.cloud.dept.topApi.service.impl;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.topApi.service.RoleTopApiService;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleTopApiServiceImpl implements RoleTopApiService {

    @Autowired
    private AuthenService authenService;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private GradeClassDao classDao;

    //学生详细信息查询。
    //"result":{
    //        "name":"name",
    //        "class_id":123,
    //        "grade_id":123,
    //        "period_id":123,
    //        "campus_id":123,
    //        "student_no":"121",
    //        "guardians":[
    //            {
    //                    "guardian_userid":"123",
    //                    "relation":"M",
    //                    "nick":"mather"
    //            }
    //        ]
    //    }
    @Override
    public ServiceResult getEduStudent(Long topClassId, String studentUserId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/student/get");
        OapiEduStudentGetRequest req = new OapiEduStudentGetRequest();
        req.setClassId(topClassId);
        req.setStudentUserid(studentUserId);
        OapiEduStudentGetResponse rsp = client.execute(req, accessToken);
        return ServiceResult.success(rsp);
    }

    //通过班级id查询班级中学生详细信息查询。
    //"result":{
    //        "has_more":true,
    //        "list":[
    //            {
    //                    "name":"name",
    //                    "student_no":"123",
    //                    "student_userid":"123",
    //                    "campus_id":123,
    //                    "period_id":123,
    //                    "grade_id":123,
    //                    "class_id":123
    //            }
    //        ],
    //        "next_cursor":10
    //    }
    @Override
    public ServiceResult listAndSaveEduStudent(Integer schoolId, Long topClassId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/student/list");
        OapiEduStudentListRequest req = new OapiEduStudentListRequest();
        req.setClassId(topClassId);
        Boolean flag = true;
        Long pageNo = 1L;
        List<OapiEduStudentListResponse.StudentRespone> allList = new ArrayList<>();
        while(flag){
            req.setPageNo(pageNo);
            req.setPageSize(10L);
            OapiEduStudentListResponse rsp = client.execute(req, accessToken);
            OapiEduStudentListResponse.PageResult result = rsp.getResult();
            List<OapiEduStudentListResponse.StudentRespone> list = result.getList();
            for (OapiEduStudentListResponse.StudentRespone studentRespone : list) {
                //todo
                //学生学号存储
                String studentUserid = studentRespone.getStudentUserid();
                String studentNo = studentRespone.getStudentNo();
                String name = studentRespone.getName();
                Student byUserId = studentDao.getByUserId(studentUserid);
                Student student = new Student();
                student.setUserId(studentUserid);
                student.setStudentNo(studentNo);
                student.setStudentName(name);
                student.setSchoolId(schoolId);
                if(byUserId != null){
                    studentDao.update(student);
                }else{
                    studentDao.insert(student);
                }
            }
            allList.addAll(list);
            if(result.getHasMore()){
                pageNo += 1;
            }else{
                flag = false;
            }
        }
        return ServiceResult.success(allList);
    }

    @Override
    public ServiceResult listEduStudent(Long classId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/student/list");
        OapiEduStudentListRequest req = new OapiEduStudentListRequest();
        req.setClassId(classId);
        Boolean flag = true;
        Long pageNo = 1L;
        List<OapiEduStudentListResponse.StudentRespone> allList = new ArrayList<>();
        while(flag){
            req.setPageNo(pageNo);
            req.setPageSize(10L);
            OapiEduStudentListResponse rsp = client.execute(req, accessToken);
            OapiEduStudentListResponse.PageResult result = rsp.getResult();
            List<OapiEduStudentListResponse.StudentRespone> list = result.getList();
            allList.addAll(list);
            if(result.getHasMore()){
                pageNo += 1;
            }else{
                flag = false;
            }
        }
        return ServiceResult.success(allList);
    }

    //查询班级下老师详细信息。
    //"result":{
    //        "grade_id":123,
    //        "campus_id":123,
    //        "is_adviser":1,
    //        "teacher_name":"name",
    //        "class_id":1234
    //    }
    @Override
    public ServiceResult getEduTeacher(Integer schoolId, Long topClassId, String teacherUserId) throws ApiException {
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/teacher/get");
        OapiEduTeacherGetRequest req = new OapiEduTeacherGetRequest();
        req.setClassId(topClassId);
        req.setTeacherUserid(teacherUserId);
        OapiEduTeacherGetResponse rsp = client.execute(req, accessToken);
        return ServiceResult.success(rsp);
    }

    //查询班级中所有老师和班主任信息。
    //"result":[
    //        {
    //                "period_id":123,
    //                "grade_id":123,
    //                "is_adviser":1,
    //                "campus_id":123,
    //                "teacher_name":"name",
    //                "teacher_userid":"userid"
    //        }
    //    ]
    @Override
    public ServiceResult listEduTeacher(Long topClassId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/teacher/list");
        OapiEduTeacherListRequest req = new OapiEduTeacherListRequest();
        req.setClassId(topClassId);
        OapiEduTeacherListResponse rsp = client.execute(req, accessToken);
        return ServiceResult.success(rsp);
    }

    //查询家长详细信息。
    //"result":{
    //        "nick":"妈妈",
    //        "student_userid":"123",
    //        "guardian_userid":"123",
    //        "relation":"M"
    //    }
    @Override
    public OapiEduGuardianGetResponse getEduParent(Long topClassId, String parentUserId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/guardian/get");
        OapiEduGuardianGetRequest req = new OapiEduGuardianGetRequest();
        req.setClassId(topClassId);
        req.setGuardianUserid(parentUserId);
        OapiEduGuardianGetResponse rsp = client.execute(req, accessToken);
        return rsp;
    }

    //查询班级中家长列表信息
    //"result":{
    //        "list":[
    //            {
    //                    "relation":"M",
    //                    "guardian_userid":"123",
    //                    "student_userid":"123",
    //                    "nick":"妈妈"
    //            }
    //        ],
    //        "has_more":true,
    //        "next_cursor":10
    //    }
    @Override
    public ServiceResult listAndSaveEduParent(Integer schoolId, Long topClassId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/guardian/list");
        OapiEduGuardianListRequest req = new OapiEduGuardianListRequest();
        req.setClassId(topClassId);
        Boolean flag = true;
        Long pageNo = 1L;
        List<OapiEduGuardianListResponse.GuardianRespone> allList = new ArrayList<>();
        while(flag){
            req.setPageNo(pageNo);
            req.setPageSize(10L);
            OapiEduGuardianListResponse rsp = client.execute(req, accessToken);
            OapiEduGuardianListResponse.PageResult result = rsp.getResult();
            List<OapiEduGuardianListResponse.GuardianRespone> list = result.getList();
            for (OapiEduGuardianListResponse.GuardianRespone guardianRespone : list) {
                String guardianUserid = guardianRespone.getGuardianUserid();
                String nick = guardianRespone.getNick();
                Parent parent = new Parent();
                parent.setUserId(guardianUserid);
                parent.setSchoolId(schoolId);
                Parent parentInSchool = parentDao.getParentInSchool(parent);
                parent.setParentName(nick);
                if(parentInSchool != null){
                    parentDao.insert(parent);
                }else{
                    parentDao.update(parent);
                }
            }
            allList.addAll(list);
            if(result.getHasMore()){
                pageNo += 1;
            }else{
                flag = false;
            }
        }
        return ServiceResult.success(allList);
    }

    @Override
    public ServiceResult listEduParent(Long classId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/guardian/list");
        OapiEduGuardianListRequest req = new OapiEduGuardianListRequest();
        req.setClassId(classId);
        Boolean flag = true;
        Long pageNo = 1L;
        List<OapiEduGuardianListResponse.GuardianRespone> allList = new ArrayList<>();
        while(flag){
            req.setPageNo(pageNo);
            req.setPageSize(10L);
            OapiEduGuardianListResponse rsp = client.execute(req, accessToken);
            OapiEduGuardianListResponse.PageResult result = rsp.getResult();
            List<OapiEduGuardianListResponse.GuardianRespone> list = result.getList();
            allList.addAll(list);
            if(result.getHasMore()){
                pageNo += 1;
            }else{
                flag = false;
            }
        }
        return ServiceResult.success(allList);
    }


    //查询当前用户所有角色身份以及其所在的班级列表。
    //"result":{
    //        "advisor": [123,345],
    //        "teacher": [123,345],
    //        "student": [123,345],
    //        "guardian":[123,345],
    //        "userid":"123345-1234"
    //    }

    @Override
    public ServiceResult getEduRoles(String userId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/roles/get");
        OapiEduRolesGetRequest req = new OapiEduRolesGetRequest();
        req.setUserid(userId);
        OapiEduRolesGetResponse rsp = client.execute(req, accessToken);
        return ServiceResult.success(rsp);
    }

    @Override
    public String getAdvisorUserIdInClass(Long deptId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/teacher/list");
        OapiEduTeacherListRequest req = new OapiEduTeacherListRequest();
        req.setClassId(deptId);
        OapiEduTeacherListResponse rsp = client.execute(req, accessToken);
        List<OapiEduTeacherListResponse.TeacherRespone> result = rsp.getResult();
        String advisorTeacherUserId = "";
        for (OapiEduTeacherListResponse.TeacherRespone teacherRespone : result) {
            if(teacherRespone.getIsAdviser().equals(new Long("1"))){
                advisorTeacherUserId = teacherRespone.getTeacherUserid();
            }
        }
        return advisorTeacherUserId;
    }

}
