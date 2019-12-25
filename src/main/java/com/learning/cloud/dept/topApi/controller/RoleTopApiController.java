package com.learning.cloud.dept.topApi.controller;

import com.dingtalk.api.response.OapiEduGuardianGetResponse;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.topApi.service.RoleTopApiService;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleTopApiController {

    @Autowired
    private RoleTopApiService roleTopApiService;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private GradeClassDao gradeClassDao;

    @GetMapping("/getEduStudent")
    public ServiceResult getEduStudent(Long topClassId, String studentUserId, String accessToken) throws ApiException {
        return roleTopApiService.getEduStudent(topClassId, studentUserId, accessToken);
    }

    @GetMapping("/listAndSaveEduStudent")
    public ServiceResult listAndSaveEduStudent(Integer schoolId, Long topClassId, String accessToken) throws ApiException {
        return roleTopApiService.listAndSaveEduStudent(schoolId, topClassId, accessToken);
    }

    @GetMapping("/listAndSaveEduParent")
    public ServiceResult listAndSaveEduParent(Integer schoolId, Long topClassId, String accessToken) throws ApiException {
        return roleTopApiService.listAndSaveEduParent(schoolId, topClassId, accessToken);
    }

    @GetMapping("/getEduParent")
    public ServiceResult getEduParent(Long topClassId, String userId, String accessToken) throws ApiException {
        OapiEduGuardianGetResponse response = roleTopApiService.getEduParent(topClassId, userId, accessToken);
        OapiEduGuardianGetResponse.GuardianRespone result = response.getResult();
        return ServiceResult.success(result);
    }

    @GetMapping("/getStudentUserIdByParent")
    public JsonResult getStudentUserIdByParent(String userId, Integer classId) throws ApiException {
        GradeClass byId = gradeClassDao.getById(classId);
        Long topClassId = byId.getDeptId();
        Integer schoolId = byId.getSchoolId();
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        OapiEduGuardianGetResponse response = roleTopApiService.getEduParent(topClassId, userId, accessToken);
        OapiEduGuardianGetResponse.GuardianRespone result = response.getResult();
        List<String> useridList = new ArrayList<>();
        if (result == null) {
            return JsonResultUtil.error(0, "请求不到学生userId");
        } else {
            List<OapiEduGuardianGetResponse.Relations> relations = result.getRelations();
            for (OapiEduGuardianGetResponse.Relations relation : relations) {
                String studentUserid = relation.getStudentUserid();
                useridList.add(studentUserid);
            }
        }
        return JsonResultUtil.success(useridList);
    }

    @GetMapping("/getEduRoles")
    public ServiceResult getEduRoles(String userId, String accessToken) throws ApiException {
        return roleTopApiService.getEduRoles(userId, accessToken);
    }

    @GetMapping("/listEduParent")
    public ServiceResult listEduParent(Long classId, String accessToken) throws ApiException {
        return roleTopApiService.listEduParent(classId, accessToken);
    }

    @GetMapping("/listEduStudent")
    public ServiceResult listEduStudent(Long classId, String accessToken) throws ApiException {
        return roleTopApiService.listEduStudent(classId, accessToken);
    }

    @GetMapping("/getAdvisorUserIdInClass")
    public ServiceResult getAdvisorUserIdInClass(Long deptId, String accessToken) throws ApiException {
        String userId = roleTopApiService.getAdvisorUserIdInClass(deptId, accessToken);
        return ServiceResult.success(userId);
    }
}
