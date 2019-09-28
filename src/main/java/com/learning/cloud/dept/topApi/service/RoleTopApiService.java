package com.learning.cloud.dept.topApi.service;

import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface RoleTopApiService {
    ServiceResult getEduStudent(Integer schoolId, Long topClassId, String studentUserId) throws ApiException;

    ServiceResult listEduStudent(Integer schoolId, Long topClassId) throws ApiException;

    ServiceResult getEduTeacher(Integer schoolId, Long topClassId, String teacherUserId) throws ApiException;

    ServiceResult listEduTeacher(Integer schoolId, Long topClassId) throws ApiException;

    ServiceResult getEduParent(Integer schoolId, Long topClassId, String parentUserId) throws ApiException;

    ServiceResult listEduParent(Integer schoolId, Long topClassId) throws ApiException;

    ServiceResult getEduRoles(Integer schoolId, String userId) throws ApiException;
}
