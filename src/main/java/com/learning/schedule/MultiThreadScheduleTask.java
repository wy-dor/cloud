package com.learning.schedule;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.learning.cloud.bizData.service.BizDataMediumService;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.course.service.CourseExchangeService;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.index.dao.AuthCorpInfoDao;
import com.learning.cloud.index.dao.AuthUserInfoDao;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.AuthCorpInfo;
import com.learning.cloud.index.entity.AuthUserInfo;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.index.service.LoggedService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.bizData.dao.SyncBizDataDao;
import com.learning.cloud.bizData.entity.SyncBizData;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
@EnableAsync
public class MultiThreadScheduleTask {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private SyncBizDataDao syncBizDataDao;

    @Autowired
    private BureauDao bureauDao;

    @Autowired
    private AuthCorpInfoDao authCorpInfoDao;

    @Autowired
    private CorpAgentDao corpAgentDao;

    @Autowired
    private AuthUserInfoDao authUserInfoDao;

    @Autowired
    private BizDataMediumService bizDataMediumService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private LoggedService loggedService;

    @Autowired
    private CourseExchangeService courseExchangeService;

    @Value("${spring.suiteId}")
    private String suiteId;

    private Map<String, Boolean> statusMap = new HashMap<>();

    @Async
    @Scheduled(cron = "0 0 1 * * ?")//每天1点
    public void addSchoolActivityScore() throws Exception {
        loggedService.AddSchoolScoreFormActivity();
    }

    @Async
    @Scheduled(cron = "0 0/1 * * * ?")//每隔一分钟
    public void queryBizData() throws Exception {
        //标志正在操作
        String subscribeId = suiteId + "_0";
        //获取企业授权应用最新状态的数据
        List<SyncBizData> bizData_04 = syncBizDataDao.getBizData(4);
        if (bizData_04 != null && bizData_04.size() > 0) {
            for (SyncBizData bizData : bizData_04) {
                String corpId = bizData.getCorpId();
                try {
                    Long id = bizData.getId();
                    Boolean status = statusMap.get(corpId);
                    if (status != null && status == true) {
                        return;
                    }
                    statusMap.put(corpId, true);
                    Map<String, Object> parse_0 = (Map<String, Object>) JSON.parse(bizData.getBizData());
                    String syncAction = (String) parse_0.get("syncAction");
                    if ("org_suite_auth".equals(syncAction)) {
                        Integer schoolId = -1;
                        String accessToken = "";
                        //更新授权企业表
                        Map<String, Object> auth_corp_info = (Map<String, Object>) parse_0.get("auth_corp_info");
                        AuthCorpInfo authCorpInfo = new AuthCorpInfo();
                        String corpName = (String) auth_corp_info.get("corp_name");
                        String industry = (String) auth_corp_info.get("industry");
                        authCorpInfo.setCorpId(corpId);
                        authCorpInfo.setCorpName(corpName);
                        authCorpInfo.setIndustry(industry);
                        authCorpInfo.setAuthLevel((Integer) auth_corp_info.get("auth_level"));
                        authCorpInfo.setInviteUrl((String) auth_corp_info.get("invite_url"));
                        if ((Boolean) auth_corp_info.get("is_authenticated")) {
                            authCorpInfo.setIsAuthenticated((short) 1);
                        } else {
                            authCorpInfo.setIsAuthenticated((short) 0);
                        }
                        authCorpInfo.setLicenseCode((String) auth_corp_info.get("license_code"));
                        if (industry.equals("初中等教育")) {
                            School school = new School();
                            authCorpInfo.setIndustryType(1);
                            school.setSchoolName(corpName);
                            school.setCorpId(corpId);
                            List<School> bySchool = schoolDao.getBySchool(school);
                            if (bySchool == null || bySchool.size() == 0) {
                                schoolDao.insert(school);
                                schoolId = school.getId();
                            } else {
                                schoolId = bySchool.get(0).getId();
                            }

                        } else if (industry.equals("教育行政机构")) {
                            authCorpInfo.setIndustryType(2);
                            Bureau byBureauName = bureauDao.getByBureauName(corpName);
                            if (byBureauName == null) {
                                Bureau bureau = new Bureau();
                                bureau.setBureauName(corpName);
                                bureau.setCorpId(corpId);
                                accessToken = authenService.getAccessToken(corpId);
                                Long orgUserCount = deptService.getOrgUserCount(accessToken, 0L);
                                Long orgActiveUserCount = deptService.getOrgUserCount(accessToken, 1L);
                                bureau.setOrgUserCount(orgUserCount);
                                bureau.setOrgActiveUserCount(orgActiveUserCount);
                                bureauDao.insert(bureau);
                            }
                        }
                        AuthCorpInfo corpInfoByCorpId = authCorpInfoDao.getCorpInfoByCorpId(corpId);
                        int i = 0;
                        if (corpInfoByCorpId == null) {
                            i = authCorpInfoDao.insert(authCorpInfo);
                        } else {
                            i = authCorpInfoDao.update(authCorpInfo);
                        }
                        if (i == 1) {
                            System.out.println("保存授权企业信息成功");
                        }

                        //获取企业凭证
                        accessToken = authenService.getAccessToken(corpId);

                        //保存应用信息
                        Map<String, Object> auth_info = (Map<String, Object>) parse_0.get("auth_info");
                        List<Map<String, Object>> agent = (List<Map<String, Object>>) auth_info.get("agent");
                        for (Map<String, Object> a : agent) {
                            CorpAgent ca = new CorpAgent();
                            ca.setAgentId(((Integer) a.get("agentid")).toString());
                            ca.setCorpId(corpId);
                            ca.setAgentName((String) a.get("agent_name"));
                            ca.setAppId(((Integer) a.get("appid")).toString());
                            ca.setLogoUrl((String) a.get("logo_url"));
                            ca.setUpdateTime(new Date());
                            CorpAgent byCorpId = corpAgentDao.getByCorpId(corpId);
                            if (byCorpId == null) {
                                corpAgentDao.insert(ca);
                            } else {
                                corpAgentDao.update(ca);
                            }
                            System.out.println("保存应用信息成功！");

                        }


                        //保存授权用户信息
                        Map<String, Object> auth_user_info = (Map<String, Object>) parse_0.get("auth_user_info");
                        AuthUserInfo userInfo = new AuthUserInfo();
                        userInfo.setUserId((String) auth_user_info.get("userId"));
                        userInfo.setCorpId(corpId);
                        AuthUserInfo byCorpId = authUserInfoDao.getByCorpId(corpId);
                        int k = 0;
                        if (byCorpId == null) {
                            k = authUserInfoDao.insert(userInfo);
                        } else {
                            k = authUserInfoDao.update(userInfo);
                        }
                        if (k == 1) {
                            System.out.println("授权用户信息保存成功");
                        }

                        //如果为教育局则添加除通讯录外的用户信息
                        if (schoolId == -1) {

                            //添加处于部门下的用户身份信息
                            Long deptId = 1L;
                            deptService.recurseGetUser(deptId, accessToken, corpId, schoolId);

                            //添加不在部门里的用户信息
                            Boolean flag = true;
                            Long offset = 0L;
                            while (flag) {
                                OapiUserListbypageResponse resp5 = deptService.getDeptUserListByPage(1L, offset, accessToken);
                                List<OapiUserListbypageResponse.Userlist> userList = resp5.getUserlist();
                                if (userList.size() < 100) {
                                    flag = false;
                                } else {
                                    offset += 1;
                                }
                                for (OapiUserListbypageResponse.Userlist user : userList) {
                                    deptService.userSaveByRole(schoolId, corpId, null, user, 5, accessToken);
                                }
                            }
                        }

                        //初始化一次班级数据
                        if (schoolId != -1) {
                            deptService.init(schoolId);
                        }
                    }
                    syncBizDataDao.updateStatus(id);
                    //实现可重复同步
                    statusMap.put(corpId, false);
                    //授权后更新一次数据
//                bizDataMediumService.initBizDataMedium();
                } catch (ApiException e) {
                    statusMap.put(corpId, false);
                }

            }
        }

    }

    @Async
    @Scheduled(cron = "0 0/5 * * * ?")//每隔五分钟
    public void queryBizDataMedium() throws Exception {
        bizDataMediumService.initBizDataMedium(null);
    }

    @Async
    @Scheduled(cron = "0 0/10 * * * ?")//每隔十分钟
    public void renewCourseExchangeAndInstanceState() throws Exception {
        courseExchangeService.renewCourseExchangeStatus(null);
    }

}
