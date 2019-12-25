package com.learning.cloud.dingCommon;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.learning.cloud.index.service.AuthenService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: yyt
 * @Date: 2019-09-03 11:02
 * @Desc:
 */
@Component
@Slf4j
public class DingUtils {

    private static String CORP_ID = "dingc5fa8a42960b8d6d35c2f4657eb6378f";
    private static String CORP_SECRET = "alPGrVK4bsLEJFS2CxAHHD7Th9HlVvhhCfcbTVIdibFMRmaJ8dpcjGuRC6Olah4R";

    @Autowired
    private AuthenService service;

    public static AuthenService authenService;

    @PostConstruct
    public void init() {
        authenService = service;
    }


    // 获取用户所在企业的token
    public static String getAccessToken(String corpId) throws Exception {
        return authenService.getAccessToken(corpId);
    }

    // 获取应用后台免登的accessToken，本接口获取的accessToken只在微应用后台管理免登服务中使用。
    public static String getIsvAccessToken() throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sso/gettoken");
        OapiSsoGettokenRequest request = new OapiSsoGettokenRequest();
        request.setCorpid(CORP_ID);
        request.setCorpsecret(CORP_SECRET);
        request.setHttpMethod("GET");
        OapiSsoGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }

    // 获取应用管理员的身份信息
    public static JsonResult getAdministrator(String code) throws Exception {
        String isvAccessToken = getIsvAccessToken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sso/getuserinfo");
        OapiSsoGetuserinfoRequest request = new OapiSsoGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");
        OapiSsoGetuserinfoResponse response = client.execute(request, isvAccessToken);
        return JsonResultUtil.success(response);
    }

    // 第三方企业应用登录，获取用户信息userid
    public static JsonResult getUserInfo(String authCode, String corpid) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = client.execute(request, getAccessToken(corpid));
        return JsonResultUtil.success(response);
    }

    // 第三方扫码登录，用户授权的临时授权码code，获取用户信息openid
    public static JsonResult getUserInfoByCode(String authCode, String appId, String appSecret) throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(authCode);
        OapiSnsGetuserinfoBycodeResponse response = client.execute(req, appId, appSecret);
        return JsonResultUtil.success(response);
    }

    // 获取用户详情
    public static OapiUserGetResponse getUserByUserid(String userid, String corpid) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userid);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, getAccessToken(corpid));
        if (response.isSuccess()) {
            return response;
        } else {
            log.error("获取用户详情失败");
            return null;
        }

    }

    // unionid换取userid
    public static String getUseridByUnionid(String unionid, String corpid) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getUseridByUnionid");
        OapiUserGetUseridByUnionidRequest request = new OapiUserGetUseridByUnionidRequest();
        request.setUnionid(unionid);
        request.setHttpMethod("GET");
        OapiUserGetUseridByUnionidResponse response = client.execute(request, getAccessToken(corpid));
        return response.getUserid();
    }
}
