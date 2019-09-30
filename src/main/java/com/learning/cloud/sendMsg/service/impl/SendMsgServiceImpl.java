package com.learning.cloud.sendMsg.service.impl;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageSendToConversationRequest;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.CorpAgent;
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

    //发送消息
    @Override
    public JsonResult sendSignLink(String classIds, Integer signId, MsgInfo msgInfo) throws Exception {
        List<String> classes = Arrays.asList(classIds.split(","));
        List<String> ps = new ArrayList<>();
        Integer schoolId = null;
        for(String classId: classes){
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
        msg.getLink().setMessageUrl("eapp://pages/signOnline/signDetail/signDetail?signId="+signId+"&link=true");
        msg.getLink().setPicUrl("https://static.dingtalk.com/media/lALPDeC2uNV20CPMkMyQ_144_144.png");
        return SendWorkMsg(workMsg,msg);
    }


    @Override
    public JsonResult sendPerformanceCard(String classIds, Integer moduleId, MsgInfo msgInfo) throws Exception {
        List<String> classes = Arrays.asList(classIds.split(","));
        List<String> ps = new ArrayList<>();
        Integer schoolId = null;
        for(String classId: classes){
            GradeClass gradeClass = gradeClassDao.getById(Integer.valueOf(classId));
            String teacherDepId = String.valueOf(gradeClass.getTDeptId());
            ps.add(teacherDepId);
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
        msg.setMsgtype("action_card");
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(msgInfo.getTitle());
        msg.getActionCard().setMarkdown(msgInfo.getText());
        msg.getActionCard().setSingleTitle("查看成绩");
        msg.getActionCard().setSingleUrl("eapp://pages/gradeReport/studentReport/studentReport?moduleId="+moduleId);
        return SendWorkMsg(workMsg,msg);
    }
}
