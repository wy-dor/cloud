package com.learning.cloud.dept.topApi.service;

import com.dingtalk.api.response.*;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

import java.util.List;

public interface DeptTopApiService {

    ServiceResult initTopClassIdInSchool(Integer schoolId, String accessToken) throws ApiException;

    ServiceResult getTopCampusList(String accessToken) throws ApiException;

    List<OapiEduCampusListResponse.CampusResponse> getAndSaveCampusList(Integer schoolId, String accessToken) throws ApiException;

    ServiceResult getProcessInstance(Integer schoolId, Long topCampusId) throws ApiException;

    List<OapiEduPeriodListResponse.PeriodResponse> getAndSavePeriodList(Integer schoolId, String accessToken, Long topCampusId) throws ApiException;

    List<OapiEduGradeListResponse.GradeResponse> getAndSaveGradeList(Integer schoolId, String accessToken, Long topPeriodId) throws ApiException;

    List<OapiEduClassListResponse.ClassResponse> getAndSaveClassList(Integer schoolId, String accessToken, Long topGradeId) throws ApiException;

    ServiceResult getEduClass(Integer schoolId, Long topClassId) throws ApiException;
}
