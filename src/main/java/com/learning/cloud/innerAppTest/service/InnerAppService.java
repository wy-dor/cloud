package com.learning.cloud.innerAppTest.service;

import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface InnerAppService {
    String getToken() throws ApiException;

    ServiceResult getSchoolDeptIds() throws ApiException;
}
