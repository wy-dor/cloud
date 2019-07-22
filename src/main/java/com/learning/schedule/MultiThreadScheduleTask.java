package com.learning.schedule;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiRoleSimplelistResponse;
import com.learning.cloud.bizData.service.BizDataMediumService;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.index.dao.AuthAppInfoDao;
import com.learning.cloud.index.dao.AuthCorpInfoDao;
import com.learning.cloud.index.dao.AuthUserInfoDao;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.AuthAppInfo;
import com.learning.cloud.index.entity.AuthCorpInfo;
import com.learning.cloud.index.entity.AuthUserInfo;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.score.dao.ScoreRecordDao;
import com.learning.cloud.score.dao.ScoreboardDao;
import com.learning.cloud.score.entity.ClassScoreboard;
import com.learning.cloud.score.entity.SchoolScoreboard;
import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.cloud.user.admin.entity.Administrator;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.bizData.dao.SyncBizDataDao;
import com.learning.cloud.bizData.dao.SyncBizDataMediumDao;
import com.learning.cloud.bizData.entity.SyncBizData;
import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import com.learning.schedule.config.Constant;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
@EnableAsync
public class MultiThreadScheduleTask {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private ScoreRecordDao scoreRecordDao;

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private ScoreboardDao scoreboardDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private AuthAppInfoDao authAppInfoDao;

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

    @Async
    @Scheduled(cron = "0 0 5 * * ?") //每天5点
    public void refreshSchoolScore() throws InterruptedException {
        //刷新学校的积分
        //1。获取是所有学校
        List<School> schools = schoolDao.getSchools();
        for(School school : schools){
            double teacherAvg = 0.0;
            double parentAvg = 0.0;
            //获取所有老师
            List<Teacher> teachers = teacherDao.getTeacherIds(school.getId().longValue());
            //取老师最新的积分
            Long sum_teacher_score = new Long(0);
            int ts = teachers.size();
            if(ts>0){
                for(Teacher te: teachers){
                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
                    Long score = new Long(0);
                    if(last!=null){
                        score = last.getScore()==null?0:last.getScore();
                    }
                    sum_teacher_score += score;
                }
                teacherAvg = sum_teacher_score/ts;
            }else{
                teacherAvg = 0.0;
            }
            //取家长最新积分
            List<Parent> parents = parentDao.getParents(school.getId().longValue());
            Long sum_parent_score = new Long(0);
            int ps = parents.size();
            if(ps>0){
                for(Parent pa : parents){
                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(pa.getUserId());
                    Long score = new Long(0);
                    if(last!=null){
                        score = last.getScore()==null?0:last.getScore();
                    }
                    if(score!=0){
                        sum_parent_score += score;
                    }else {
                        ps--;
                    }
                }
                if(ps>0){
                    parentAvg = sum_parent_score/ps;
                }
            }else {
                parentAvg = 0.0;
            }

            //计算最终的积分
            double score = teacherAvg*0.8 + parentAvg*0.2;
            //保存到数据库表中
            SchoolScoreboard schoolScoreboard = new SchoolScoreboard();
            schoolScoreboard.setSchoolId(school.getId().longValue());
            schoolScoreboard.setScore(new Double(score).intValue());
            schoolScoreboard.setBureauId(school.getBureauId().longValue());
            int i = scoreboardDao.addSchoolScoreboard(schoolScoreboard);
        }
    }


    @Async
    @Scheduled(cron = "0 0 6 * * ?") //每天6点
    public void refreshClassScore() throws InterruptedException {
        //刷新班级的积分
        //1。获取所有班级
        List<GradeClass> gradeClasses = gradeClassDao.getAllClass();
        for(GradeClass gd : gradeClasses){
            double teacherAvg = 0.0;
            double parentAvg = 0.0;
            //获取所有老师
            List<Teacher> teachers = teacherDao.getClassTeachers(gd);
            //取老师最新的积分
            Long sum_teacher_score = new Long(0);
            int ts = teachers.size();
            if(ts>0){
                for(Teacher te: teachers){
                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
                    Long score = new Long(0);
                    if(last!=null){
                        score = last.getScore()==null?0:last.getScore();
                    }
                    sum_teacher_score += score;
                }
                teacherAvg = sum_teacher_score/ts;
            }else{
                teacherAvg = 0.0;
            }
            //取家长最新积分
            List<Parent> parents = parentDao.getParents(gd.getId().longValue());
            Long sum_parent_score = new Long(0);
            int ps = parents.size();
            if(ps>0){
                for(Parent pa : parents){
                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(pa.getUserId());
                    Long score = new Long(0);
                    if(last!=null){
                        score = last.getScore()==null?0:last.getScore();
                    }
                    if(score!=0){
                        sum_parent_score += score;
                    }else {
                        ps--;
                    }
                }
                if(ps>0){
                    parentAvg = sum_parent_score/ps;
                }
            }else {
                parentAvg = 0.0;
            }
            //计算最终的积分
            double score = teacherAvg*0.8 + parentAvg*0.2;
            //保存到数据库表中
            ClassScoreboard classScoreboard = new ClassScoreboard();
            classScoreboard.setClassId(gd.getId().longValue());
            classScoreboard.setScore(new Double(score).intValue());
            classScoreboard.setSchoolId(gd.getSchoolId().longValue());
            classScoreboard.setBureauId(gd.getBureauId().longValue());
            int i = scoreboardDao.addClassScoreboard(classScoreboard);
        }
    }

//    @Async
//    @Scheduled(cron = "0 0/1 * * * ?")//每隔一分钟
//    public void refreshAgent() throws InterruptedException, ApiException {
//        List<AuthAppInfo> infos = authAppInfoDao.getToAuthorize();
//        if(infos != null && infos.size() > 0){
//            for (AuthAppInfo info : infos) {
//                authenService.authenApp(info.getCorpId());
//            }
//        }
//
//    }

    @Async
    @Scheduled(cron = "0 0/1 * * * ?")//每隔一分钟
    public void queryBizData() throws InterruptedException, ApiException {
        String subscribeId = Constant.SUITE_ID + "_0";
        //获取企业授权应用最新状态的数据
        List<SyncBizData> bizData_04 = syncBizDataDao.getBizData(subscribeId, 4);
        for (SyncBizData bizData : bizData_04) {
            Long id = bizData.getId();
            Map<String, Object> parse_0 = (Map<String, Object>) JSON.parse(bizData.getBizData());
            String syncAction = (String)parse_0.get("syncAction");
            if("org_suite_auth".equals(syncAction)){
                //更新授权企业表
                Map<String,Object> auth_corp_info = (Map<String,Object>) parse_0.get("auth_corp_info");
                AuthCorpInfo authCorpInfo = new AuthCorpInfo();
                String corpId = (String) auth_corp_info.get("corpid");
                String corpName = (String) auth_corp_info.get("corp_name");
                String industry = (String) auth_corp_info.get("industry");
                authCorpInfo.setCorpId(corpId);
                authCorpInfo.setCorpName(corpName);
                authCorpInfo.setIndustry(industry);
                authCorpInfo.setAuthLevel((Integer) auth_corp_info.get("auth_level"));
                authCorpInfo.setInviteUrl((String) auth_corp_info.get("invite_url"));
                if((Boolean) auth_corp_info.get("is_authenticated")){
                    authCorpInfo.setIsAuthenticated((short)1);
                }else{
                    authCorpInfo.setIsAuthenticated((short)0);
                }
                authCorpInfo.setLicenseCode((String) auth_corp_info.get("license_code"));
                if(industry.equals("初中等教育")){
                    School school = new School();
                    authCorpInfo.setIndustryType(1);
                    school.setSchoolName(corpName);
                    school.setCorpId(corpId);
                    List<School> bySchool = schoolDao.getBySchool(school);
                    if(bySchool == null || bySchool.size() == 0){
                        schoolDao.insert(school);
                    }

                    //todo
                    /*更新管理员表*/
                    /*OapiRoleListResponse rsp = getRoleList(accessToken);
                    List<OapiRoleListResponse.OpenRole> roles = rsp.getResult().getList().get(0).getRoles();
                    for (OapiRoleListResponse.OpenRole role : roles) {
                        *//*更新管理员信息*//*
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
                    }*/

                }else if(industry.equals("教育行政机构")){
                    authCorpInfo.setIndustryType(2);
                    Bureau byBureauName = bureauDao.getByBureauName(corpName);
                    if(byBureauName == null){
                        Bureau bureau = new Bureau();
                        bureau.setBureauName(corpName);
                        bureau.setCorpId(corpId);
                        bureauDao.insert(bureau);
                    }
                }
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

                //保存应用信息
                Map<String,Object> auth_info = (Map<String,Object>) parse_0.get("auth_info");
                List<Map<String,Object>> agent = (List<Map<String,Object>>) auth_info.get("agent");
                for (Map<String, Object> a : agent) {
                    CorpAgent ca = new CorpAgent();
                    ca.setAgentId(((Integer)a.get("agentid")).toString());
                    ca.setCorpId(corpId);
                    ca.setAgentName((String)a.get("agent_name"));
                    ca.setAppId(((Integer)a.get("appid")).toString());
                    ca.setLogoUrl((String)a.get("logo_url"));
                    ca.setUpdateTime(new Date());
                    CorpAgent byCorpId = corpAgentDao.getByCorpId(corpId);
                    if(byCorpId == null){
                        corpAgentDao.insert(ca);
                    }else{
                        corpAgentDao.update(ca);
                    }
                    System.out.println("保存应用信息成功！");
                }

                //保存授权用户信息
                Map<String,Object> auth_user_info = (Map<String,Object>) parse_0.get("auth_user_info");
                AuthUserInfo userInfo = new AuthUserInfo();
                userInfo.setUserId((String) auth_user_info.get("userId"));
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
            }

            syncBizDataDao.updateStatus(id);
        }

        //更新推送的suite_ticket
        List<SyncBizData> bizDataList = syncBizDataDao.getBizData(subscribeId, 2);
        if(bizDataList != null){
            for (SyncBizData bizData_02 : bizDataList) {
                Long id = bizData_02.getId();
                String corpId = bizData_02.getCorpId();
                Map<String, String> parse = (Map<String, String>) JSON.parse(bizData_02.getBizData());
                String suiteTicket = parse.get("suiteTicket");
                String accessToken = authenService.getURLAccessToken(corpId, suiteTicket);
                AuthAppInfo byCorpId = authAppInfoDao.findByCorpId(corpId);
                AuthAppInfo authAppInfo = new AuthAppInfo();
                authAppInfo.setCorpId(corpId);
                authAppInfo.setSuiteTicket(suiteTicket);
                authAppInfo.setCorpAccessToken(accessToken);
                if(byCorpId == null){
                    authAppInfo.setCreatedTime(new Date());
                    authAppInfoDao.insert(authAppInfo);
                }else{
                    authAppInfoDao.update(authAppInfo);
                }
                syncBizDataDao.updateStatus(id);
            }
        }

    }

    @Async
    @Scheduled(cron = "0 0 6,18 * * ?")//每天的早上六点和下午六点
    public void queryBizDataMedium() throws Exception{
        bizDataMediumService.initBizDataMedium();
    }
}
