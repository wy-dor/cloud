package com.learning.cloud.sendMsg.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageSendToConversationRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageSendToConversationResponse;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.sendMsg.entity.GeneralMsg;
import com.learning.cloud.sendMsg.entity.MsgInfo;
import com.learning.cloud.sendMsg.entity.WorkMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.learning.cloud.dingCommon.DingUtils.getAccessToken;
import static com.learning.utils.CommonUtils.StringListToString;

/**
 * @Author: yyt
 * @Date: 2019-09-17 11:16
 * @Desc: 发送消息
 */
@Slf4j
public class SendUtils {

    private static String SEND_WORK_MSG_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
    private static String SEND_GENERAL_MSG_URL = "https://oapi.dingtalk.com/message/send_to_conversation";

//    @Autowired
//    private CorpAgentDao dao;
//
//    public static CorpAgentDao corpAgentDao;
//
//    @PostConstruct
//    public void init(){
//        corpAgentDao = dao;
//    }


    /**
     * 发送工作通知消息
     * 企业工作通知会话中某个微应用的名义推送到员工的通知消息
     */
    public static void SendWorkMsg(WorkMsg workMsg, OapiMessageCorpconversationAsyncsendV2Request.Msg msg)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient(SEND_WORK_MSG_URL);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(Long.valueOf(workMsg.getAgentId()));
        request.setUseridList(StringListToString(workMsg.getUserIdList()));
        request.setDeptIdList(StringListToString(workMsg.getDeptIdList()));
        request.setToAllUser(workMsg.getToAllUser());
        request.setMsg(msg);
        // 发送消息
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,getAccessToken(workMsg.getCorpId()));
        if(response.isSuccess()){
            log.info("发送消息成功：corpid"+workMsg.getCorpId());
        }else {
            log.info("发送消息失败"+response.getMsg());
        }
    }


    /**
     * 发送普通消息
     * 员工个人在使用应用时，可以通过界面操作的方式往群或其他人的会话里推送消息
     */
    public static void SendGeneralMsg(GeneralMsg generalMsg, OapiMessageSendToConversationRequest.Msg msg)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient(SEND_GENERAL_MSG_URL);

        OapiMessageSendToConversationRequest request = new OapiMessageSendToConversationRequest();
        request.setSender(generalMsg.getSender());
        request.setCid(generalMsg.getCid());

        if(msg.getMsgtype().equals("text")){
            OapiMessageSendToConversationRequest.Text text = new OapiMessageSendToConversationRequest.Text();
            text.setContent("测试测试");
            msg.setText(text);
            msg.setMsgtype("text");
        }else if(msg.getMsgtype().equals("image")){
            OapiMessageSendToConversationRequest.Image image = new OapiMessageSendToConversationRequest.Image();
            image.setMediaId("@lADOdvRYes0CbM0CbA");
            msg.setImage(image);
            msg.setMsgtype("image");
        }else if(msg.getMsgtype().equals("file")){
            OapiMessageSendToConversationRequest.File file = new OapiMessageSendToConversationRequest.File();
            file.setMediaId("@lADOdvRYes0CbM0CbA");
            msg.setFile(file);
            msg.setMsgtype("file");
        }else if(msg.getMsgtype().equals("markdown")){
            OapiMessageSendToConversationRequest.Markdown markdown = new OapiMessageSendToConversationRequest.Markdown();
            markdown.setText("# 这是支持markdown的文本 \\n## 标题2  \\n* 列表1 \\n![alt 啊](https://img.alicdn.com/tps/TB1XLjqNVXXXXc4XVXXXXXXXXXX-170-64.png)");
            markdown.setTitle("首屏会话透出的展示内容");
            msg.setMarkdown(markdown);
            msg.setMsgtype("markdown");
        }else if(msg.getMsgtype().equals("action_card")){
            OapiMessageSendToConversationRequest.ActionCard actionCard = new OapiMessageSendToConversationRequest.ActionCard();
            actionCard.setTitle("是透出到会话列表和通知的文案");
            actionCard.setMarkdown("持markdown格式的正文内");
            actionCard.setSingleTitle("查看详情");
            actionCard.setSingleUrl("https://open.dingtalk.com");
            msg.setActionCard(actionCard);
            msg.setMsgtype("action_card");
        }else if(msg.getMsgtype().equals("link")){
            OapiMessageSendToConversationRequest.Link link = new OapiMessageSendToConversationRequest.Link();
            link.setMessageUrl("https://www.baidu.com/");
            link.setPicUrl("@lADOdvRYes0CbM0CbA");
            link.setText("步扬测试");
            link.setTitle("oapi");
            msg.setLink(link);
            msg.setMsgtype("link");
        }
        request.setMsg(msg);
        OapiMessageSendToConversationResponse response = client.execute(request, getAccessToken(generalMsg.getCorpId()));
        if(response.isSuccess()){
            log.info("发送消息成功：corpid"+generalMsg.getCorpId());
        }else {
            log.info("发送消息失败"+response.getMsg());
        }
    }

}
