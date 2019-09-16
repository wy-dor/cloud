package com.learning.cloud.conversation.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationGetsendprogressRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationGetsendresultRequest;
import com.dingtalk.api.request.OapiMessageSendToConversationRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendprogressResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendresultResponse;
import com.dingtalk.api.response.OapiMessageSendToConversationResponse;
import com.learning.cloud.conversation.service.ConversationService;
import com.learning.cloud.index.service.AuthenService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private AuthenService authenService;

    @Override
    public OapiMessageSendToConversationResponse sendToConversation(String userId, String cid, String msgBody, String corpId) throws ApiException {
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/message/send_to_conversation");

        OapiMessageSendToConversationRequest req = new OapiMessageSendToConversationRequest();
        req.setSender(userId);
        req.setCid(cid);
        OapiMessageSendToConversationRequest.Msg msg = new OapiMessageSendToConversationRequest.Msg();

        Map<String,Object> parse = (Map<String,Object>)JSON.parse(msgBody);
        String msgtype = parse.get("msgtype").toString();

        // 文本消息
        if(msgtype.equals("text")){
            OapiMessageSendToConversationRequest.Text text = new OapiMessageSendToConversationRequest.Text();
            Map<String, Object> parse_text = (Map<String, Object>) parse.get("text");
            text.setContent(parse_text.get("content").toString());
            msg.setText(text);
            msg.setMsgtype("text");
            req.setMsg(msg);
        }

        // link消息
        if(msgtype.equals("link")){
            OapiMessageSendToConversationRequest.Link link = new OapiMessageSendToConversationRequest.Link();
            Map<String, Object> parse_link = (Map<String, Object>) parse.get("link");
            link.setMessageUrl(parse_link.get("messageUrl").toString());
            link.setPicUrl(parse_link.get("picUrl").toString());
            link.setText(parse_link.get("text").toString());
            link.setTitle(parse_link.get("title").toString());
            msg.setLink(link);
            msg.setMsgtype("link");
            req.setMsg(msg);
        }

        OapiMessageSendToConversationResponse response = client.execute(req, accessToken);
        return response;
    }

    //以上为普通消息通知接口
    //=============================
    //以下为工作通知消息接口

    public OapiMessageCorpconversationAsyncsendV2Response asyncSend_v2(Long agentId,String corpId) throws ApiException {
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList("01376814877479");
        request.setAgentId(153858650L);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent("test123");
        request.setMsg(msg);

        msg.setMsgtype("image");
        msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
        msg.getImage().setMediaId("@lADOdvRYes0CbM0CbA");
        request.setMsg(msg);

        msg.setMsgtype("file");
        msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
        msg.getFile().setMediaId("@lADOdvRYes0CbM0CbA");
        request.setMsg(msg);

        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle("test");
        msg.getLink().setText("test");
        msg.getLink().setMessageUrl("test");
        msg.getLink().setPicUrl("test");
        request.setMsg(msg);

        msg.setMsgtype("markdown");
        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
        msg.getMarkdown().setText("##### text");
        msg.getMarkdown().setTitle("### Title");
        request.setMsg(msg);

        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().getHead().setText("head");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setContent("xxx");
        msg.setMsgtype("oa");
        request.setMsg(msg);

        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle("xxx123411111");
        msg.getActionCard().setMarkdown("### 测试123111");
        msg.getActionCard().setSingleTitle("测试测试");
        msg.getActionCard().setSingleUrl("https://www.baidu.com");
        msg.setMsgtype("action_card");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,accessToken);
        return null;
    }

    public OapiMessageCorpconversationGetsendprogressResponse getSendProgress(Long agentId,Long taskId,String corpId) throws ApiException {
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendprogress");
        OapiMessageCorpconversationGetsendprogressRequest request  = new OapiMessageCorpconversationGetsendprogressRequest();
        request.setAgentId(agentId);
        request.setTaskId(taskId);
        OapiMessageCorpconversationGetsendprogressResponse response = client.execute(request, accessToken);
        return response;
    }

    @Override
    public OapiMessageCorpconversationGetsendresultResponse getSendResult(Long agentId, Long taskId, String corpId) throws ApiException {
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendresult");
        OapiMessageCorpconversationGetsendresultRequest request  = new OapiMessageCorpconversationGetsendresultRequest();
        request.setAgentId(agentId);
        request.setTaskId(taskId);
        OapiMessageCorpconversationGetsendresultResponse response = client.execute(request, accessToken);
        return response;
    }

}
