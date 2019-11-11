package com.learning.cloud.dept.topApi.service;

import com.dingtalk.api.response.OapiEduGuardianGetResponse;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface RoleTopApiService {
    ServiceResult getEduStudent(Long topClassId, String studentUserId, String accessToken) throws ApiException;

    ServiceResult listAndSaveEduStudent(Integer schoolId, Long topClassId, String accessToken) throws ApiException;

    ServiceResult listEduStudent(Long classId, String accessToken) throws ApiException;

    ServiceResult getEduTeacher(Integer schoolId, Long topClassId, String teacherUserId) throws ApiException;

    ServiceResult listEduTeacher(Long topClassId, String accessToken) throws ApiException;

    OapiEduGuardianGetResponse getEduParent(Long topClassId, String parentUserId, String accessToken) throws ApiException;

    ServiceResult listAndSaveEduParent(Integer schoolId, Long topClassId, String accessToken) throws ApiException;

    ServiceResult listEduParent(Long classId, String accessToken) throws ApiException;

    ServiceResult getEduRoles(String userId, String accessToken) throws ApiException;
}
