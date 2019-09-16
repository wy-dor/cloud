package com.learning.cloud.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.learning.cloud.bizData.dao.SyncBizDataDao;
import com.learning.cloud.bizData.entity.SyncBizData;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.config.ApiUrlConstant;
import com.learning.cloud.config.Constant;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.index.dao.*;
import com.learning.cloud.index.entity.AuthAppInfo;
import com.learning.cloud.index.entity.AuthCorpInfo;
import com.learning.cloud.index.entity.AuthUserInfo;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.admin.dao.AdministratorDao;
import com.learning.cloud.user.admin.entity.Administrator;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class AuthenServiceImpl implements AuthenService {

    @Autowired
    private AuthAppInfoDao authAppInfoDao;

    @Autowired
    private SyncBizDataDao syncBizDataDao;

    @Value("${spring.suiteKey}")
    private String suiteKey;

    @Value("${spring.suiteSecret}")
    private String suiteSecret;

    /*获取企业凭证*/
    @Override
    public String getAccessToken(String authCorpId) throws ApiException {
//        AuthAppInfo byCorpId = authAppInfoDao.findByCorpId(authCorpId);
//        String suiteTicket = byCorpId.getSuiteTicket();
        SyncBizData forSuiteTicket = syncBizDataDao.getForSuiteTicket();
        if (forSuiteTicket == null){
            return "";
        }
        Map<String, String> parse = (Map<String, String>) JSON.parse(forSuiteTicket.getBizData());
        String suiteTicket = parse.get("suiteTicket");
        String accessToken = getURLAccessToken(authCorpId,suiteTicket);
//        String accessToken = byCorpId.getCorpAccessToken();
//        Date updateTime = byCorpId.getUpdateTime();
//        Date now = new Date();
//        /*accessToken两小时过期*/
//        long minutes = (now.getTime() - updateTime.getTime()) / 1000 / 60;
//        if(minutes >= 120){
//            String suiteTicket = byCorpId.getSuiteTicket();
//            accessToken = getURLAccessToken(authCorpId,suiteTicket);
//            byCorpId.setCorpAccessToken(accessToken);
//            byCorpId.setUpdateTime(new Date());
//            authAppInfoDao.update(byCorpId);
//        }
        return accessToken;
    }

    @Override
    public String getURLAccessToken(String authCorpId, String suiteTicket) throws ApiException {
        String accessToken;
        DefaultDingTalkClient client = new DefaultDingTalkClient(ApiUrlConstant.URL_GET_CORP_TOKEN);
        OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
        req.setAuthCorpid(authCorpId);
        OapiServiceGetCorpTokenResponse execute = client.execute(req, suiteKey, suiteSecret, suiteTicket);
        accessToken = execute.getAccessToken();
        return accessToken;
    }

    /*获取角色下的员工列表*/
    @Override
    public OapiRoleSimplelistResponse getRoleSimpleList(long roleId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/simplelist");
        OapiRoleSimplelistRequest request = new OapiRoleSimplelistRequest();
        request.setRoleId(roleId);
        request.setOffset(0L);
        request.setSize(10L);
        OapiRoleSimplelistResponse response = client.execute(request, accessToken);
        return response;
    }

    /*获取所有的角色列表*/
    @Override
    public OapiRoleListResponse getRoleList(String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/list");
        OapiRoleListRequest request = new OapiRoleListRequest();
        request.setOffset(0L);
        request.setSize(10L);
        OapiRoleListResponse response = client.execute(request, accessToken);
        return response;
    }

}
