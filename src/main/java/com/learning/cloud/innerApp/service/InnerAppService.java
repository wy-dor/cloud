package com.learning.cloud.innerApp.service;

import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface InnerAppService {
    String getToken() throws ApiException;

    ServiceResult getSchoolDeptIds() throws ApiException;

    String getUserId(String accessToken, String authCode) throws ApiException;
}
