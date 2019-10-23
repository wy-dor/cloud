//package com.learning.schedule;
//
//import com.alibaba.fastjson.JSON;
//import com.dingtalk.api.response.OapiUserListbypageResponse;
//import com.learning.cloud.bizData.service.BizDataMediumService;
//import com.learning.cloud.bureau.dao.BureauDao;
//import com.learning.cloud.bureau.entity.Bureau;
//import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
//import com.learning.cloud.dept.gradeClass.entity.GradeClass;
//import com.learning.cloud.dept.manage.service.DeptService;
//import com.learning.cloud.index.dao.AuthAppInfoDao;
//import com.learning.cloud.index.dao.AuthCorpInfoDao;
//import com.learning.cloud.index.dao.AuthUserInfoDao;
//import com.learning.cloud.index.dao.CorpAgentDao;
//import com.learning.cloud.index.entity.AuthCorpInfo;
//import com.learning.cloud.index.entity.AuthUserInfo;
//import com.learning.cloud.index.entity.CorpAgent;
//import com.learning.cloud.index.service.AuthenService;
//import com.learning.cloud.school.dao.SchoolDao;
//import com.learning.cloud.school.entity.School;
//import com.learning.cloud.score.dao.ScoreRecordDao;
//import com.learning.cloud.score.dao.ScoreboardDao;
//import com.learning.cloud.score.entity.ClassScoreboard;
//import com.learning.cloud.score.entity.SchoolScoreboard;
//import com.learning.cloud.score.entity.ScoreRecord;
//import com.learning.cloud.user.parent.dao.ParentDao;
//import com.learning.cloud.user.parent.entity.Parent;
//import com.learning.cloud.user.teacher.dao.TeacherDao;
//import com.learning.cloud.user.teacher.entity.Teacher;
//import com.learning.cloud.bizData.dao.SyncBizDataDao;
//import com.learning.cloud.bizData.entity.SyncBizData;
//import com.taobao.api.ApiException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@EnableScheduling
//@EnableAsync
//public class MultiThreadScheduleTask {
//
//    @Autowired
//    private SchoolDao schoolDao;
//
//    @Autowired
//    private TeacherDao teacherDao;
//
//    @Autowired
//    private ScoreRecordDao scoreRecordDao;
//
//    @Autowired
//    private ParentDao parentDao;
//
//    @Autowired
//    private ScoreboardDao scoreboardDao;
//
//    @Autowired
//    private GradeClassDao gradeClassDao;
//
//    @Autowired
//    private AuthAppInfoDao authAppInfoDao;
//
//    @Autowired
//    private AuthenService authenService;
//
//    @Autowired
//    private SyncBizDataDao syncBizDataDao;
//
//    @Autowired
//    private BureauDao bureauDao;
//
//    @Autowired
//    private AuthCorpInfoDao authCorpInfoDao;
//
//    @Autowired
//    private CorpAgentDao corpAgentDao;
//
//    @Autowired
//    private AuthUserInfoDao authUserInfoDao;
//
//    @Autowired
//    private BizDataMediumService bizDataMediumService;
//
//    @Autowired
//    private DeptService deptService;
//
//    @Value("${spring.suiteId}")
//    private String suiteId;
//
//    private Map<String, Boolean> statusMap = new HashMap<>();
//
//
//    @Async
//    @Scheduled(cron = "0 0 5 * * ?") //每天5点
//    public void refreshSchoolScore() throws InterruptedException {
//        //刷新学校的积分
//        //1。获取是所有学校
//        List<School> schools = schoolDao.getSchools();
//        for (School school : schools) {
//            double teacherAvg = 0.0;
//            double parentAvg = 0.0;
//            //获取所有老师
//            List<Teacher> teachers = teacherDao.getTeacherIds(school.getId().longValue());
//            //取老师最新的积分
//            Long sum_teacher_score = new Long(0);
//            int ts = teachers.size();
//            if (ts > 0) {
//                for (Teacher te : teachers) {
//                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
//                    Long score = new Long(0);
//                    if (last != null) {
//                        score = last.getScore() == null ? 0 : last.getScore();
//                    }
//                    sum_teacher_score += score;
//                }
//                teacherAvg = sum_teacher_score / ts;
//            } else {
//                teacherAvg = 0.0;
//            }
//            //取家长最新积分
//            List<Parent> parents = parentDao.getParents(school.getId().longValue());
//            Long sum_parent_score = new Long(0);
//            int ps = parents.size();
//            if (ps > 0) {
//                for (Parent pa : parents) {
//                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(pa.getUserId());
//                    Long score = new Long(0);
//                    if (last != null) {
//                        score = last.getScore() == null ? 0 : last.getScore();
//                    }
//                    if (score != 0) {
//                        sum_parent_score += score;
//                    } else {
//                        ps--;
//                    }
//                }
//                if (ps > 0) {
//                    parentAvg = sum_parent_score / ps;
//                }
//            } else {
//                parentAvg = 0.0;
//            }
//
//            //计算最终的积分
//            double score = teacherAvg * 0.8 + parentAvg * 0.2;
//            //保存到数据库表中
//            SchoolScoreboard schoolScoreboard = new SchoolScoreboard();
//            schoolScoreboard.setSchoolId(school.getId().longValue());
//            schoolScoreboard.setScore(new Double(score).intValue());
//            schoolScoreboard.setBureauId(school.getBureauId().longValue());
//            int i = scoreboardDao.addSchoolScoreboard(schoolScoreboard);
//        }
//    }
//
//
//    @Async
//    @Scheduled(cron = "0 0 6 * * ?") //每天6点
//    public void refreshClassScore() throws InterruptedException {
//        //刷新班级的积分
//        //1。获取所有班级
//        List<GradeClass> gradeClasses = gradeClassDao.getAllClass();
//        for (GradeClass gd : gradeClasses) {
//            double teacherAvg = 0.0;
//            double parentAvg = 0.0;
//            //获取所有老师
//            List<Teacher> teachers = teacherDao.getClassTeachers(gd);
//            //取老师最新的积分
//            Long sum_teacher_score = new Long(0);
//            int ts = teachers.size();
//            if (ts > 0) {
//                for (Teacher te : teachers) {
//                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
//                    Long score = new Long(0);
//                    if (last != null) {
//                        score = last.getScore() == null ? 0 : last.getScore();
//                    }
//                    sum_teacher_score += score;
//                }
//                teacherAvg = sum_teacher_score / ts;
//            } else {
//                teacherAvg = 0.0;
//            }
//            //取家长最新积分
//            List<Parent> parents = parentDao.getParents(gd.getId().longValue());
//            Long sum_parent_score = new Long(0);
//            int ps = parents.size();
//            if (ps > 0) {
//                for (Parent pa : parents) {
//                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(pa.getUserId());
//                    Long score = new Long(0);
//                    if (last != null) {
//                        score = last.getScore() == null ? 0 : last.getScore();
//                    }
//                    if (score != 0) {
//                        sum_parent_score += score;
//                    } else {
//                        ps--;
//                    }
//                }
//                if (ps > 0) {
//                    parentAvg = sum_parent_score / ps;
//                }
//            } else {
//                parentAvg = 0.0;
//            }
//            //计算最终的积分
//            double score = teacherAvg * 0.8 + parentAvg * 0.2;
//            //保存到数据库表中
//            ClassScoreboard classScoreboard = new ClassScoreboard();
//            classScoreboard.setClassId(gd.getId().longValue());
//            classScoreboard.setScore(new Double(score).intValue());
//            classScoreboard.setSchoolId(gd.getSchoolId().longValue());
//            classScoreboard.setBureauId(gd.getBureauId().longValue());
//            int i = scoreboardDao.addClassScoreboard(classScoreboard);
//        }
//    }
//
////    @Async
////    @Scheduled(cron = "0 0/1 * * * ?")//每隔一分钟
////    public void refreshAgent() throws InterruptedException, ApiException {
////        List<AuthAppInfo> infos = authAppInfoDao.getToAuthorize();
////        if(infos != null && infos.size() > 0){
////            for (AuthAppInfo info : infos) {
////                authenService.authenApp(info.getCorpId());
////            }
////        }
////
////    }
//
//    @Async
//    @Scheduled(cron = "0 0/1 * * * ?")//每隔一分钟
//    public void queryBizData() throws Exception {
//        //标志正在操作
//        String subscribeId = suiteId + "_0";
//        //获取企业授权应用最新状态的数据
//        List<SyncBizData> bizData_04 = syncBizDataDao.getBizData(subscribeId, 4);
//        if (bizData_04 != null && bizData_04.size() > 0) {
//            for (SyncBizData bizData : bizData_04) {
//                String corpId = bizData.getCorpId();
//                try {
//                    Long id = bizData.getId();
//                    Boolean status = statusMap.get(corpId);
//                    if (status != null && status == true) {
//                        return;
//                    }
//                    statusMap.put(corpId, true);
//                    Map<String, Object> parse_0 = (Map<String, Object>) JSON.parse(bizData.getBizData());
//                    String syncAction = (String) parse_0.get("syncAction");
//                    if ("org_suite_auth".equals(syncAction)) {
//                        Integer schoolId = -1;
//                        String accessToken = "";
//                        //更新授权企业表
//                        Map<String, Object> auth_corp_info = (Map<String, Object>) parse_0.get("auth_corp_info");
//                        AuthCorpInfo authCorpInfo = new AuthCorpInfo();
//                        String corpName = (String) auth_corp_info.get("corp_name");
//                        String industry = (String) auth_corp_info.get("industry");
//                        authCorpInfo.setCorpId(corpId);
//                        authCorpInfo.setCorpName(corpName);
//                        authCorpInfo.setIndustry(industry);
//                        authCorpInfo.setAuthLevel((Integer) auth_corp_info.get("auth_level"));
//                        authCorpInfo.setInviteUrl((String) auth_corp_info.get("invite_url"));
//                        if ((Boolean) auth_corp_info.get("is_authenticated")) {
//                            authCorpInfo.setIsAuthenticated((short) 1);
//                        } else {
//                            authCorpInfo.setIsAuthenticated((short) 0);
//                        }
//                        authCorpInfo.setLicenseCode((String) auth_corp_info.get("license_code"));
//                        if (industry.equals("初中等教育")) {
//                            School school = new School();
//                            authCorpInfo.setIndustryType(1);
//                            school.setSchoolName(corpName);
//                            school.setCorpId(corpId);
//                            List<School> bySchool = schoolDao.getBySchool(school);
//                            if (bySchool == null || bySchool.size() == 0) {
//                                schoolDao.insert(school);
//                                schoolId = school.getId();
//                            } else {
//                                schoolId = bySchool.get(0).getId();
//                            }
//
//                        } else if (industry.equals("教育行政机构")) {
//                            authCorpInfo.setIndustryType(2);
//                            Bureau byBureauName = bureauDao.getByBureauName(corpName);
//                            if (byBureauName == null) {
//                                Bureau bureau = new Bureau();
//                                bureau.setBureauName(corpName);
//                                bureau.setCorpId(corpId);
//                                accessToken = authenService.getAccessToken(corpId);
//                                Long orgUserCount = deptService.getOrgUserCount(accessToken, 0L);
//                                Long orgActiveUserCount = deptService.getOrgUserCount(accessToken, 1L);
//                                bureau.setOrgUserCount(orgUserCount);
//                                bureau.setOrgActiveUserCount(orgActiveUserCount);
//                                bureauDao.insert(bureau);
//                            }
//                        }
//                        AuthCorpInfo corpInfoByCorpId = authCorpInfoDao.getCorpInfoByCorpId(corpId);
//                        int i = 0;
//                        if (corpInfoByCorpId == null) {
//                            i = authCorpInfoDao.insert(authCorpInfo);
//                        } else {
//                            i = authCorpInfoDao.update(authCorpInfo);
//                        }
//                        if (i == 1) {
//                            System.out.println("保存授权企业信息成功");
//                        }
//
//                        //更新授权应用表
//                        /*SyncBizData forSuiteTicket = syncBizDataDao.getForSuiteTicket();
//                        if (forSuiteTicket != null) {
//                            Map<String, String> parse = (Map<String, String>) JSON.parse(forSuiteTicket.getBizData());
//                            String suiteTicket = parse.get("suiteTicket");
//                            accessToken = authenService.getURLAccessToken(corpId, suiteTicket);
//                            AuthAppInfo info = authAppInfoDao.findByCorpId(corpId);
//                            AuthAppInfo authAppInfo = new AuthAppInfo();
//                            authAppInfo.setCorpId(corpId);
//                            authAppInfo.setCorpName(corpName);
//                            authAppInfo.setSuiteTicket(suiteTicket);
//                            authAppInfo.setCorpAccessToken(accessToken);
//                            if (info == null) {
//                                authAppInfo.setCreatedTime(new Date());
//                                authAppInfoDao.insert(authAppInfo);
//                            } else {
//                                authAppInfoDao.update(authAppInfo);
//                            }
//                            syncBizDataDao.updateStatus(forSuiteTicket.getId());
//                        }else {
//                            throw new Exception("获取不到suiteTicket推送");
//                        }*/
//
//                        //获取企业凭证
//                        accessToken = authenService.getAccessToken(corpId);
//
//                        //保存应用信息
//                        Map<String, Object> auth_info = (Map<String, Object>) parse_0.get("auth_info");
//                        List<Map<String, Object>> agent = (List<Map<String, Object>>) auth_info.get("agent");
//                        for (Map<String, Object> a : agent) {
//                            CorpAgent ca = new CorpAgent();
//                            ca.setAgentId(((Integer) a.get("agentid")).toString());
//                            ca.setCorpId(corpId);
//                            ca.setAgentName((String) a.get("agent_name"));
//                            ca.setAppId(((Integer) a.get("appid")).toString());
//                            ca.setLogoUrl((String) a.get("logo_url"));
//                            ca.setUpdateTime(new Date());
//                            CorpAgent byCorpId = corpAgentDao.getByCorpId(corpId);
//                            if (byCorpId == null) {
//                                corpAgentDao.insert(ca);
//                            } else {
//                                corpAgentDao.update(ca);
//                            }
//                            System.out.println("保存应用信息成功！");
////                            List<String> adminList = (List<String>) a.get("admin_list");
////                            //更新管理员表
////                            for (String userId : adminList) {
////                                //administrator表和user表同步
////                                deptService.userSaveByRole(schoolId, corpId, null, userId, 5, accessToken);
////                            }
//
//                        }
//
//
//                        //保存授权用户信息
//                        Map<String, Object> auth_user_info = (Map<String, Object>) parse_0.get("auth_user_info");
//                        AuthUserInfo userInfo = new AuthUserInfo();
//                        userInfo.setUserId((String) auth_user_info.get("userId"));
//                        userInfo.setCorpId(corpId);
//                        AuthUserInfo byCorpId = authUserInfoDao.getByCorpId(corpId);
//                        int k = 0;
//                        if (byCorpId == null) {
//                            k = authUserInfoDao.insert(userInfo);
//                        } else {
//                            k = authUserInfoDao.update(userInfo);
//                        }
//                        if (k == 1) {
//                            System.out.println("授权用户信息保存成功");
//                        }
//
//                        //如果为教育局则添加除通讯录外的用户信息
//                        if (schoolId == -1) {
//
//                            //添加处于部门下的用户身份信息
//                            Long deptId = 1L;
//                            deptService.recurseGetUser(deptId, accessToken, corpId, schoolId);
//
//                            //添加不在部门里的用户信息
//                            Boolean flag = true;
//                            Long offset = 0L;
//                            while (flag) {
//                                OapiUserListbypageResponse resp5 = deptService.getDeptUserListByPage(1L, offset, accessToken);
//                                List<OapiUserListbypageResponse.Userlist> userList = resp5.getUserlist();
//                                if (userList.size() < 100) {
//                                    flag = false;
//                                } else {
//                                    offset += 1;
//                                }
//                                for (OapiUserListbypageResponse.Userlist user : userList) {
//                                    deptService.userSaveByRole(schoolId, corpId, null, user, 5, accessToken);
//                                }
//                            }
//                        }
//
//                        //初始化一次班级数据
//                        if (schoolId != -1) {
//                            deptService.init(schoolId);
//                        }
//                    }
//                    syncBizDataDao.updateStatus(id);
//                    //实现可重复同步
//                    statusMap.put(corpId, false);
//                    //授权后更新一次数据
////                bizDataMediumService.initBizDataMedium();
//                } catch (ApiException e) {
//                    statusMap.put(corpId, false);
//                }
//
//            }
//        }
//
//        //更新推送的suite_ticket
////        List<SyncBizData> bizDataList = syncBizDataDao.getBizData(subscribeId, 2);
////        if (bizDataList != null && bizDataList.size() != 0) {
////            for (SyncBizData bizData_02 : bizDataList) {
////                Long id = bizData_02.getId();
////                Map<String, String> parse = (Map<String, String>) JSON.parse(bizData_02.getBizData());
////                String suiteTicket = parse.get("suiteTicket");
////                List<AuthCorpInfo> corpInfos = authCorpInfoDao.getCorpInfos();
////                for (AuthCorpInfo corpInfo : corpInfos) {
////                    String corpId = corpInfo.getCorpId();
////                    String corpName = corpInfo.getCorpName();
////                    String accessToken = authenService.getURLAccessToken(corpId, suiteTicket);
////                    if (accessToken == null) {
////                        continue;
////                    }
////                    AuthAppInfo byCorpId = authAppInfoDao.findByCorpId(corpId);
////                    AuthAppInfo authAppInfo = new AuthAppInfo();
////                    authAppInfo.setCorpId(corpId);
////                    authAppInfo.setCorpName(corpName);
////                    authAppInfo.setSuiteTicket(suiteTicket);
////                    authAppInfo.setCorpAccessToken(accessToken);
////                    if (byCorpId == null) {
////                        authAppInfo.setCreatedTime(new Date());
////                        authAppInfoDao.insert(authAppInfo);
////                    } else {
////                        authAppInfoDao.update(authAppInfo);
////                    }
////                }
////                //标识已操作
////                syncBizDataDao.updateStatus(id);
////            }
////        }
//
//    }
//
//    @Async
//    @Scheduled(cron = "0 0/5 * * * ?")//每隔十分钟
//    public void queryBizDataMedium() throws Exception {
//        bizDataMediumService.initBizDataMedium(null);
//    }
//}
