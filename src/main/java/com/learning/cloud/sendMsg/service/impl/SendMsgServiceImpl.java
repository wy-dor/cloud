package com.learning.cloud.sendMsg.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiEduStudentGetRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiEduStudentGetResponse;
import com.learning.cloud.bill.dao.ParentBillDao;
import com.learning.cloud.bill.entity.ParentBill;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.topApi.service.RoleTopApiService;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.sendMsg.entity.MsgInfo;
import com.learning.cloud.sendMsg.entity.WorkMsg;
import com.learning.cloud.sendMsg.service.SendMsgService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.learning.cloud.sendMsg.utils.SendUtils.SendWorkMsg;

/**
 * @Author: yyt
 * @Date: 2019-09-17 10:58
 * @Desc:
 */
@Slf4j
@Service
public class SendMsgServiceImpl implements SendMsgService {

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private CorpAgentDao corpAgentDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private RoleTopApiService roleTopApiService;

    @Autowired
    private ParentBillDao parentBillDao;

    //发送消息
    @Override
    public JsonResult sendSignLink(String classIds, Integer signId, MsgInfo msgInfo) throws Exception {
        List<String> classes = Arrays.asList(classIds.split(","));
        List<String> ps = new ArrayList<>();
        Integer schoolId = null;
        for (String classId : classes) {
            GradeClass gradeClass = gradeClassDao.getById(Integer.valueOf(classId));
            String parentDepId = String.valueOf(gradeClass.getPDeptId());
            ps.add(parentDepId);
            schoolId = gradeClass.getSchoolId();
        }
        School school = schoolDao.getBySchoolId(schoolId);
        // 获取agent_id
        CorpAgent corpAgent = corpAgentDao.getByCorpId(school.getCorpId());
        WorkMsg workMsg = new WorkMsg();
        workMsg.setCorpId(school.getCorpId());
        workMsg.setDeptIdList(ps);
        workMsg.setAgentId(corpAgent.getAgentId());
        workMsg.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle(msgInfo.getTitle());
        msg.getLink().setText(msgInfo.getText());
        msg.getLink().setMessageUrl("eapp://pages/signOnline/signDetail/signDetail?signId=" + signId + "&link=true");
        msg.getLink().setPicUrl("https://static.dingtalk.com/media/lALPDeC2uNV20CPMkMyQ_144_144.png");
        return SendWorkMsg(workMsg, msg);
    }

    @Override
    public JsonResult sendDutyMsgToAdvisor(Integer classId, String date, MsgInfo msgInfo) throws Exception {
        GradeClass gradeClass = gradeClassDao.getById(classId);
        Integer schoolId = gradeClass.getSchoolId();
        School school = schoolDao.getBySchoolId(schoolId);
        String corpId = school.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        Long deptId = gradeClass.getDeptId();
        String advisorUserIdInClass = roleTopApiService.getAdvisorUserIdInClass(deptId, accessToken);
        if (advisorUserIdInClass.equals("")) {
            return JsonResultUtil.success("该班级下暂时没有设置班主任");
        }
        // 获取agent_id
        CorpAgent corpAgent = corpAgentDao.getByCorpId(corpId);
        WorkMsg workMsg = new WorkMsg();
        workMsg.setCorpId(corpId);
        workMsg.setAgentId(corpAgent.getAgentId());
        List<String> userIdList = new ArrayList<>();
        userIdList.add(advisorUserIdInClass);
        workMsg.setUserIdList(userIdList);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle(msgInfo.getTitle());
        msg.getLink().setText(msgInfo.getText());
        msg.getLink().setMessageUrl("eapp://pages/duty/classHygiene/classHygiene?classId=" + classId + "&date=" + date);
        msg.getLink().setPicUrl("https://static.dingtalk.com/media/lALPDeC2uNV20CPMkMyQ_144_144.png");
        return SendWorkMsg(workMsg, msg);
    }


    @Override
    public JsonResult sendPerformanceCard(String classIds, Integer moduleId, MsgInfo msgInfo) throws Exception {
        List<String> classes = Arrays.asList(classIds.split(","));
        String cId = classes.get(0);
        GradeClass gc = gradeClassDao.getById(Integer.valueOf(cId));
        Integer schoolId = gc.getSchoolId();
        School school = schoolDao.getBySchoolId(schoolId);
        // 获取agent_id
        CorpAgent corpAgent = corpAgentDao.getByCorpId(school.getCorpId());
        Boolean success = true;
        for (String classId : classes) {
            List<String> ps = new ArrayList<>();
            GradeClass gradeClass = gradeClassDao.getById(Integer.valueOf(classId));
            String parentDeptId = String.valueOf(gradeClass.getPDeptId());
            ps.add(parentDeptId);
            WorkMsg workMsg = new WorkMsg();
            workMsg.setToAllUser(false);
            workMsg.setCorpId(school.getCorpId());
            workMsg.setDeptIdList(ps);
            workMsg.setAgentId(corpAgent.getAgentId());
            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype("action_card");
            msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
            msg.getActionCard().setTitle(msgInfo.getTitle());
            msg.getActionCard().setMarkdown(msgInfo.getText());
            msg.getActionCard().setSingleTitle("查看成绩");
            msg.getActionCard().setSingleUrl("eapp://pages/gradeReport/studentReport/studentReport?moduleId=" + moduleId + "&classId=" + classId);
            JsonResult jsonResult = SendWorkMsg(workMsg, msg);
            Integer code = jsonResult.getCode();
            if (code != 1) {
                success = false;
            }
        }
        if (success) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(0, "还有班级成绩未发送成功");
        }
    }

    @Override
    public JsonResult sendBillChargeCard(Integer parentId, MsgInfo msgInfo) throws Exception {
        ParentBill parentBill = parentBillDao.getParentBillById(parentId);
        Integer schoolId = parentBill.getSchoolId();
        School school = schoolDao.getBySchoolId(schoolId);
        CorpAgent corpAgent = corpAgentDao.getByCorpId(school.getCorpId());
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("action_card");
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(msgInfo.getTitle());
        msg.getActionCard().setMarkdown(msgInfo.getText());
        msg.getActionCard().setSingleTitle("查看详情");
        msg.getActionCard().setSingleUrl("eapp://pages/pay/parent/index/index?classId=" + parentBill.getClassId());
        WorkMsg workMsg = new WorkMsg();
        workMsg.setToAllUser(false);
        workMsg.setCorpId(school.getCorpId());
        workMsg.setAgentId(corpAgent.getAgentId());
        List<String> stuUserIdList = Arrays.asList(parentBill.getUserIdStr().split(","));
        Boolean success = true;
        Integer classId = parentBill.getClassId();
        GradeClass gradeClass = gradeClassDao.getById(classId);
        String corpId = school.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        Long deptId = gradeClass.getDeptId();
        List<String> parentUserIdList = new ArrayList<>();
        for (String userId : stuUserIdList) {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/student/get");
            OapiEduStudentGetRequest req = new OapiEduStudentGetRequest();
            req.setClassId(deptId);
            req.setStudentUserid(userId);
            OapiEduStudentGetResponse rsp = client.execute(req, accessToken);
            OapiEduStudentGetResponse.StudentRespone result = rsp.getResult();
            if(result == null){
                return JsonResultUtil.error(0, "该班级下学生信息未及时同步，请先同步班级学生信息！");
            }
            List<OapiEduStudentGetResponse.GuardianRespone> guardians = result.getGuardians();
            for (OapiEduStudentGetResponse.GuardianRespone guardian : guardians) {
                String guardianUserId = guardian.getGuardianUserid();
                parentUserIdList.add(guardianUserId);
            }
        }
        int size = parentUserIdList.size();
        int circle = size / 20;
        for (int i = 0; i <= circle; i++) {
            List<String> parentUserIdList_temp = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                int k = 20 * i + j;
                String userId = parentUserIdList.get(k);
                parentUserIdList_temp.add(userId);
                if(k == size - 1){
                    break;
                }
            }
            workMsg.setUserIdList(parentUserIdList_temp);
            JsonResult jsonResult = SendWorkMsg(workMsg, msg);
            Integer code = jsonResult.getCode();
            if (code != 1) {
                success = false;
            }
        }
        if (success) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(0, "还有账单未发送成功");
        }
    }
}
