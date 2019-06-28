package com.learning.cloud.index.service;

import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface AuthenService {

    /*应用授权*/
    ServiceResult authenApp(String corpId) throws ApiException;

    /*获取企业凭证*/
    String getAccessToken(String authCorpId) throws ApiException;

    /*更新所有企业的access_token*/
    ServiceResult updateAllAccessToken() throws ApiException;

    String getSuiteAccessToken() throws ApiException;

    /*获取企业授权信息*/
    ServiceResult getAuthInfo(String corpId) throws ApiException;
}
