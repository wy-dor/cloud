package com.learning.cloud.bizData.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.learning.cloud.bizData.dao.SyncBizDataDao;
import com.learning.cloud.bizData.entity.SyncBizData;
import com.learning.cloud.bizData.service.SyncDingPushDataService;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.index.dao.AuthCorpInfoDao;
import com.learning.cloud.index.dao.AuthUserInfoDao;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.AuthCorpInfo;
import com.learning.cloud.index.entity.AuthUserInfo;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yyt
 * @Date: 2019/10/31 10:23 上午
 * @Desc:
 */
@Service
@Slf4j
public class SyncDingPushDataServiceImpl implements SyncDingPushDataService {

    private Map<String, Boolean> runningCorp = new HashMap<>();

    @Autowired
    private SyncBizDataDao syncBizDataDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private BureauDao bureauDao;

    @Autowired
    private AuthCorpInfoDao authCorpInfoDao;

    @Autowired
    private CorpAgentDao corpAgentDao;

    @Autowired
    private AuthUserInfoDao authUserInfoDao;

    // 同步钉钉数据
    // open_sync_biz_data:
    // 当biz_type = 2时，数据为套件票据suiteTicket最新状态
    // 更新套件

    // 当biz_type = 4时，数据为企业授权应用的最新状态
    // syncAction
    // "org_suite_auth" ：表示企业授权套件: 新增企业
    // "org_suite_relieve"：表示企业解除授权: 删除企业
    // "org_suite_change"：表示企业变更授权范围

    // 当biz_type = 7时，数据为企业微应用的最新状态
    // syncAction
    // org_micro_app_restore  微应用启用
    // org_micro_app_stop  微应用停用
    // org_micro_app_remove  微应用删除，保留企业对套件的授权
    public void syncBizData() throws Exception {
        //获取open_sync_biz_data表中所有状态为0的数据
        List<SyncBizData> syncBizDataList = syncBizDataDao.getAllBizData();
        if (syncBizDataList != null && syncBizDataList.size() > 0) {
            //循环处理
            for (SyncBizData syncBizData : syncBizDataList) {
                //持续运行避免同时执行
                String corpId = syncBizData.getCorpId();
                if (!runningCorp.get("corpId") || runningCorp.get("corpId") == null) {
                    //标记为执行状态true，执行完后标记为false
                    runningCorp.put(corpId, true);
                    switch (syncBizData.getBizType()) {
                        //订单信息
                        case 17:
                            log.info("订单信息");
                            ;
                            break;
                        //订单到期或者退款导致的服务关闭
                        case 37:
                            log.info("订单到期或者退款");
                            ;
                            break;
                        default:
                            log.info("跟新企业信息");
                            updateCorpStatus(syncBizData);
                    }
                }
            }
        }
    }

    // 更新企业的最新状态/企业微应用的最新状态
    public void updateCorpStatus(SyncBizData syncBizData) {
        JSONObject jsonObject = JSONObject.parseObject(syncBizData.getBizData());
        Map<String, Object> map = (Map<String, Object>) jsonObject;
        String syncAction = String.valueOf(map.get("syncAction"));
        //企业信息

        if ("org_suite_auth".equals(syncAction)) {
            //表示企业授权套件，初始化企业信息
            authSuit(map);

        } else if ("org_suite_change".equals(syncAction)) {
            //表示企业变更授权范围,更新企业信息

        } else if ("org_suite_relieve".equals(syncAction)) {
            //表示企业解除授权，设置企业禁用状态

        } else if ("org_micro_app_restore".equals(syncAction)) {
            //微应用启用

        } else if ("org_micro_app_stop".equals(syncAction)) {
            //微应用停用

        } else if ("org_micro_app_remove".equals(syncAction)) {
            //微应用删除，保留企业对套件的授权

        }
    }


    public void authSuit(Map<String, Object> map) {
        //auth_corp_info
        Map<String,Object> authCorp = (Map<String, Object>)map.get("auth_corp_info");
        AuthCorpInfo authCorpInfo = new AuthCorpInfo();
        String corpId = String.valueOf(authCorp.get("corpid"));
        String corpName = String.valueOf(authCorp.get("corp_name"));
        String industry = String.valueOf(authCorp.get("industry"));
        authCorpInfo.setCorpId(corpId);
        authCorpInfo.setCorpName(corpName);
        authCorpInfo.setIndustry(industry);
        authCorpInfo.setAuthLevel((Integer) authCorp.get("auth_level"));
        authCorpInfo.setInviteUrl((String) authCorp.get("invite_url"));
        if ((Boolean) authCorp.get("is_authenticated")) {
            authCorpInfo.setIsAuthenticated((short) 1);
        } else {
            authCorpInfo.setIsAuthenticated((short) 0);
        }
        authCorpInfo.setLicenseCode(String.valueOf(authCorp.get("license_code")));
        Long orgUserCount = null;
        Long orgActiveUserCount = null;
        try {
            orgUserCount = deptService.getOrgUserCount(authenService.getAccessToken(corpId), 0L);
            orgActiveUserCount = deptService.getOrgUserCount(authenService.getAccessToken(corpId), 1L);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        if (industry.equals("初中等教育")) {
            School school = new School();
            authCorpInfo.setIndustryType(1);
            school.setSchoolName(corpName);
            school.setCorpId(corpId);
            school.setOrgUserCount(orgUserCount);
            school.setOrgActiveUserCount(orgActiveUserCount);
            schoolDao.insert(school);
            log.info("新增学校:"+corpName+"/"+corpId);
        } else if (industry.equals("教育行政机构")) {
            authCorpInfo.setIndustryType(2);
            Bureau bureau = new Bureau();
            bureau.setBureauName(corpName);
            bureau.setCorpId(corpId);
            bureau.setOrgUserCount(orgUserCount);
            bureau.setOrgActiveUserCount(orgActiveUserCount);
            bureauDao.insert(bureau);
            log.info("新增教育局:"+corpName+"/"+corpId);
        }
        //插入公司授权信息
         authCorpInfoDao.insert(authCorpInfo);
        log.info("新增公司授权信息");
        //auth_info 保存应用信息
        Map<String, Object> authInfo = (Map<String, Object>) map.get("auth_info");
        List<Map<String, Object>> agent = (List<Map<String, Object>>) authInfo.get("agent");
        for (Map<String, Object> a : agent) {
            CorpAgent corpAgent = new CorpAgent();
            corpAgent.setAgentId(((Integer) a.get("agentid")).toString());
            corpAgent.setCorpId(corpId);
            corpAgent.setAgentName((String) a.get("agent_name"));
            corpAgent.setAppId(((Integer) a.get("appid")).toString());
            corpAgent.setLogoUrl((String) a.get("logo_url"));
            corpAgent.setUpdateTime(new Date());
            corpAgentDao.insert(corpAgent);
        }
        log.info("新增应用授权信息");
        //auth_user_info 保存授权人信息
        Map<String, Object> authUser = (Map<String, Object>) map.get("auth_user_info");
        AuthUserInfo userInfo = new AuthUserInfo();
        userInfo.setUserId(String.valueOf(authUser.get("userId")));
        userInfo.setCorpId(corpId);
        authUserInfoDao.insert(userInfo);

        log.info("新增授权人信息");
    }


}
