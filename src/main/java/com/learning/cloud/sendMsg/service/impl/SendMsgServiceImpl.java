package com.learning.cloud.sendMsg.service.impl;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.sendMsg.entity.MsgInfo;
import com.learning.cloud.sendMsg.entity.WorkMsg;
import com.learning.cloud.sendMsg.service.SendMsgService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public JsonResult sendSignLink(Integer classId, MsgInfo msgInfo) throws Exception {
        GradeClass gradeClass = gradeClassDao.getById(classId);
        String parentDepId = String.valueOf(gradeClass.getPDeptId());
        //发送消息
        List<String> ps = new ArrayList<>();
        ps.add(parentDepId);
        // 获取agent_id
        CorpAgent corpAgent = corpAgentDao.getByCorpId(msgInfo.getCorpId());
        WorkMsg workMsg = new WorkMsg();
        workMsg.setCorpId(msgInfo.getCorpId());
        workMsg.setDeptIdList(ps);
        workMsg.setAgentId(corpAgent.getAgentId());
        workMsg.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle(msgInfo.getTitle());
        msg.getLink().setText(msgInfo.getText());
        msg.getLink().setMessageUrl(msgInfo.getMessageUrl());
        msg.getLink().setPicUrl(msgInfo.getPicUrl());
        SendWorkMsg(workMsg,msg);
        return JsonResultUtil.success();
    }
}
