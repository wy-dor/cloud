package com.learning.cloud.user.admin.service;

import com.dingtalk.api.response.OapiRoleListResponse;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface AdministratorService {

    OapiRoleListResponse getRoleList(String accessToken) throws ApiException;

    ServiceResult getRoleSimpleList(long roleId, String accessToken) throws ApiException;

}
