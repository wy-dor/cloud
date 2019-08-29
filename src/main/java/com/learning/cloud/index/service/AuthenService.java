package com.learning.cloud.index.service;

import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiRoleSimplelistResponse;
import com.taobao.api.ApiException;

public interface AuthenService {

    /*获取企业凭证*/
    String getAccessToken(String authCorpId) throws ApiException;

    String getURLAccessToken(String authCorpId, String suiteTicket) throws ApiException;

    OapiRoleSimplelistResponse getRoleSimpleList(long roleId, String accessToken) throws ApiException;

    OapiRoleListResponse getRoleList(String accessToken) throws ApiException;
}

