package com.learning.cloud.dept.topApi.service;

import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface DeptTopApiService {
    ServiceResult getAndSaveCampusList(Integer schoolId) throws ApiException;

    ServiceResult getProcessInstance(Integer schoolId, Long topCampusId) throws ApiException;

    ServiceResult getAndSavePeriodList(Integer schoolId, Long topCampusId) throws ApiException;

    ServiceResult getAndSaveGradeList(Integer schoolId, Long topPeriodId) throws ApiException;

    ServiceResult getAndSaveClassList(Integer schoolId, Long topGradeId) throws ApiException;
}
