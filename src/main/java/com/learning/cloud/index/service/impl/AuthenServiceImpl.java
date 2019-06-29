package com.learning.cloud.index.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.config.ApiUrlConstant;
import com.learning.cloud.config.Constant;
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
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AuthenServiceImpl implements AuthenService {

    private AuthAppInfo authAppInfo;

    @Autowired
    private AuthAppInfoDao authAppInfoDao;

    @Autowired
    private CallbackInfoDao callbackInfoDao;

    @Autowired
    private AuthCorpInfoDao authCorpInfoDao;

    @Autowired
    private CorpAgentDao corpAgentDao;

    @Autowired
    private AuthUserInfoDao authUserInfoDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private BureauDao bureauDao;

    @Autowired
    private AdministratorDao administratorDao;

    @Override
    public ServiceResult authenApp(String corpId) throws ApiException {
        /*获取临时授权码*/
        String authCode = callbackInfoDao.getAuthCodeByCorpId(corpId);

        authAppInfo = new AuthAppInfo();
        authAppInfo.setCreatedTime(new Date());
        authAppInfo.setTempAuthCode(authCode);

        /*获取套件令牌Token*/
        String suiteAccessToken = getSuiteAccessToken();
        authAppInfo.setSuiteAccessToken(suiteAccessToken);

        /*获取永久授权码并库存*/
        String permanentCode = getPermanentCode(authCode, suiteAccessToken);

        /*激活应用*/
        String errmsg = appActive(suiteAccessToken, permanentCode);
        if(errmsg.equals("ok")){
            System.out.println("成功激活应用！");
        }

        /*获取企业凭证*/
        /*String accessToken = getAccessToken(corpId);
        authAppInfo.setCorpAccessToken(accessToken);*/

        AuthAppInfo info = authAppInfoDao.findByCorpId(corpId);
        int i = 0;
        if(info == null){
            i = authAppInfoDao.insert(authAppInfo);

        }else{
            i = authAppInfoDao.update(authAppInfo);
        }
        if(i == 1){
            System.out.println("企业授权信息更新成功！");
        }

        /*获取企业授权信息*/
        return getAuthInfo(corpId);

    }

    /*获取套件令牌Token（获取第三方应用凭证）*/
    @Override
    public String getSuiteAccessToken() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(ApiUrlConstant.URL_GET_SUITE_TOKEN);
        OapiServiceGetSuiteTokenRequest request = new OapiServiceGetSuiteTokenRequest();
        request.setSuiteKey(Constant.SUITE_KEY);
        request.setSuiteSecret(Constant.SUITE_SECRET);
        /*钉钉推送的suiteTicket。测试应用可以随意填写。*/
        request.setSuiteTicket(Constant.SUITE_TICKET);
        OapiServiceGetSuiteTokenResponse response = client.execute(request);
        String suiteAccessToken = response.getSuiteAccessToken();
        Long expiresIn = response.getExpiresIn();
        System.out.println("access_token:" + suiteAccessToken);
        System.out.println("expires in " + expiresIn);
        return suiteAccessToken;
    }

    /*获取永久授权码并库存*/
    private String getPermanentCode(String authCode, String suiteAccessToken) throws ApiException {
        DingTalkClient client1 = new DefaultDingTalkClient(ApiUrlConstant.URL_GET_PERMANENT_CODE + suiteAccessToken);
        OapiServiceGetPermanentCodeRequest req1 = new OapiServiceGetPermanentCodeRequest();
        req1.setTmpAuthCode(authCode);
        OapiServiceGetPermanentCodeResponse rsp1 = client1.execute(req1);
        String permanentCode = rsp1.getPermanentCode();
        String authCorpId = rsp1.getAuthCorpInfo().getCorpid();
        String corpName = rsp1.getAuthCorpInfo().getCorpName();
        authAppInfo.setPermanentCode(permanentCode);
        authAppInfo.setCorpId(authCorpId);
        authAppInfo.setCorpName(corpName);
        System.out.println("permanentCode:" + permanentCode);
        System.out.println("authCorpId：" + authCorpId);
        return permanentCode;
    }

    /*激活应用*/
    private String appActive(String suiteAccessToken, String permanentCode) throws ApiException {
        DingTalkClient client2 = new DefaultDingTalkClient(ApiUrlConstant.URL_ACTIVE_SUITE + suiteAccessToken);
        OapiServiceActivateSuiteRequest req2 = new OapiServiceActivateSuiteRequest();
        req2.setSuiteKey(Constant.SUITE_KEY);
        req2.setAuthCorpid(authAppInfo.getCorpId());
        req2.setPermanentCode(permanentCode);
        OapiServiceActivateSuiteResponse rsp2 = client2.execute(req2);
        return rsp2.getErrmsg();
    }

    /*获取企业凭证*/
    @Override
    public String getAccessToken(String authCorpId) throws ApiException {
        AuthAppInfo byCorpId = authAppInfoDao.findByCorpId(authCorpId);
        String accessToken = byCorpId.getCorpAccessToken();
        Date updateTime = byCorpId.getUpdateTime();
        Date now = new Date();
        /*accessToken两小时过期*/
        long minutes = (now.getTime() - updateTime.getTime()) / 1000 / 60;
        if(minutes >= 120){
            DefaultDingTalkClient client = new DefaultDingTalkClient(ApiUrlConstant.URL_GET_CORP_TOKEN);
            OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
            req.setAuthCorpid(authCorpId);
            OapiServiceGetCorpTokenResponse execute = client.execute(req, Constant.SUITE_KEY, Constant.SUITE_SECRET, Constant.SUITE_TICKET);
            accessToken = execute.getAccessToken();
            byCorpId.setCorpAccessToken(accessToken);
            byCorpId.setUpdateTime(new Date());
            authAppInfoDao.update(byCorpId);
        }
        return accessToken;
    }

    /*更新指定企业凭证*/
    public String updateAccessToken(String authCorpId) throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient(ApiUrlConstant.URL_GET_CORP_TOKEN);
        OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
        req.setAuthCorpid(authCorpId);
        OapiServiceGetCorpTokenResponse execute = client.execute(req, Constant.SUITE_KEY,Constant.SUITE_SECRET, Constant.SUITE_TICKET);
        String accessToken = execute.getAccessToken();
        AuthAppInfo byCorpId = authAppInfoDao.findByCorpId(authCorpId);
        byCorpId.setCorpAccessToken(accessToken);
        byCorpId.setUpdateTime(new Date());
        authAppInfoDao.update(byCorpId);
        return execute.getAccessToken();
    }

    /*更新所有企业的accessToken*/
    @Override
    public ServiceResult updateAllAccessToken() throws ApiException {
        List<AuthCorpInfo> corpInfos = authCorpInfoDao.getCorpInfos();
        List<String> tokenList = new ArrayList<>();
        for (AuthCorpInfo corpInfo : corpInfos) {
            String corpId = corpInfo.getCorpId();
            String accessToken = updateAccessToken(corpId);
            tokenList.add(accessToken);
        }
        return ServiceResult.success(tokenList);
    }


    /*获取企业授权信息*/
    @Override
    public ServiceResult getAuthInfo(String corpId) throws ApiException {
        String accessToken = getAccessToken(corpId);
        /*获取企业授权信息*/
        DingTalkClient client = new DefaultDingTalkClient(ApiUrlConstant.URL_GET_AUTH_INFO);
        OapiServiceGetAuthInfoRequest req = new OapiServiceGetAuthInfoRequest();
        req.setAuthCorpid(corpId);
        OapiServiceGetAuthInfoResponse response = client.execute(req,Constant.SUITE_KEY,Constant.SUITE_SECRET, Constant.SUITE_TICKET);
        /*保存授权企业信息*/
        OapiServiceGetAuthInfoResponse.AuthCorpInfo CorpInfo = response.getAuthCorpInfo();
        AuthCorpInfo authCorpInfo = new AuthCorpInfo();
        String corpName = CorpInfo.getCorpName();
        authCorpInfo.setCorpId(corpId);
        authCorpInfo.setCorpName(corpName);
        authCorpInfo.setIndustry(CorpInfo.getIndustry());
        authCorpInfo.setAuthLevel(CorpInfo.getAuthLevel().intValue());
        authCorpInfo.setInviteUrl(CorpInfo.getInviteUrl());
        if(CorpInfo.getIsAuthenticated()){
            authCorpInfo.setIsAuthenticated((short)1);
        }else{
            authCorpInfo.setIsAuthenticated((short)0);
        }
        authCorpInfo.setLicenseCode(CorpInfo.getLicenseCode());
        AuthCorpInfo corpInfoByCorpId = authCorpInfoDao.getCorpInfoByCorpId(corpId);
        int i = 0;
        if(corpInfoByCorpId == null){
             i = authCorpInfoDao.insert(authCorpInfo);
        }else{
            i = authCorpInfoDao.update(authCorpInfo);
        }
        if(i == 1){
            System.out.println("保存授权企业信息成功");
        }

        if(corpName.endsWith("学校")){
            Integer schoolId;
            School school = new School();
            school.setSchoolName(corpName);
            List<School> bySchool = schoolDao.getBySchool(school);
            if(bySchool == null || bySchool.size() == 0){
                schoolDao.insert(school);
                schoolId = school.getId();
            }else{
                schoolId = bySchool.get(0).getId();
            }
            /*更新管理员表*/
            OapiRoleListResponse rsp = getRoleList(accessToken);
            List<OapiRoleListResponse.OpenRole> roles = rsp.getResult().getList().get(0).getRoles();
            for (OapiRoleListResponse.OpenRole role : roles) {
                /*更新管理员信息*/
                if(role.getName().contains("管理员")){
                    Long roleId = role.getId();
                    OapiRoleSimplelistResponse rsp1 = getRoleSimpleList(roleId, accessToken);
                    List<OapiRoleSimplelistResponse.OpenEmpSimple> list = rsp1.getResult().getList();
                    for (OapiRoleSimplelistResponse.OpenEmpSimple openEmpSimple : list) {
                        Administrator a = new Administrator();
                        a.setName(openEmpSimple.getName());
                        a.setUserId(openEmpSimple.getUserid());
                        a.setSchoolId(schoolId);
                        Administrator byAdm = administratorDao.getByAdm(a);
                        if(byAdm == null){
                            administratorDao.insert(a);
                        }
                    }
                }
            }

        }else{
            Bureau byBureauName = bureauDao.getByBureauName(corpName);
            if(byBureauName == null){
                Bureau bureau = new Bureau();
                bureau.setBureauName(corpName);
                bureauDao.insert(bureau);
            }
        }


        /*保存应用信息*/
        OapiServiceGetAuthInfoResponse.AuthInfo authInfo = response.getAuthInfo();
        List<OapiServiceGetAuthInfoResponse.Agent> agents = authInfo.getAgent();
        for (OapiServiceGetAuthInfoResponse.Agent agent : agents) {
            CorpAgent ca = new CorpAgent();
            ca.setAgentId(agent.getAgentid().toString());
            ca.setCorpId(corpId);
            ca.setAgentName(agent.getAgentName());
            ca.setAppId(agent.getAppid().toString());
            ca.setLogoUrl(agent.getLogoUrl());
            ca.setUpdateTime(new Date());
            CorpAgent byCorpId = corpAgentDao.getByCorpId(corpId);
            if(byCorpId == null){
                corpAgentDao.insert(ca);
            }else{
                corpAgentDao.update(ca);
            }
            System.out.println("保存应用信息成功！");

        }
        /*保存授权用户信息*/
        OapiServiceGetAuthInfoResponse.AuthUserInfo authUserInfo = response.getAuthUserInfo();
        AuthUserInfo userInfo = new AuthUserInfo();
        userInfo.setUserId(authUserInfo.getUserId());
        userInfo.setCorpId(corpId);
        AuthUserInfo byCorpId = authUserInfoDao.getByCorpId(corpId);
        int k = 0;
        if(byCorpId == null){
            k = authUserInfoDao.insert(userInfo);
        }else{
            k = authUserInfoDao.update(userInfo);
        }
        if(k == 1){
            System.out.println("授权用户信息保存成功");
        }
        return ServiceResult.success(response);
    }

    /*获取角色下的员工列表*/
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
    public OapiRoleListResponse getRoleList(String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/list");
        OapiRoleListRequest request = new OapiRoleListRequest();
        request.setOffset(0L);
        request.setSize(10L);
        OapiRoleListResponse response = client.execute(request, accessToken);
        return response;
    }

}
