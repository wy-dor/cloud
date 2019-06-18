package com.learning.cloud.index.service;

import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface AuthenService {

    /*应用授权*/
    public ServiceResult authenApp(String corpId) throws ApiException;

    /*获取企业凭证*/
    public String getAccessToken(String authCorpId) throws ApiException;

    /*获取企业授权信息*/
    public ServiceResult getAuthInfo(String corpId) throws ApiException;
}
