package com.learning.cloud.conversation.controller;

import com.dingtalk.api.response.OapiMessageCorpconversationGetsendprogressResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendresultResponse;
import com.dingtalk.api.response.OapiMessageSendToConversationResponse;
import com.learning.cloud.conversation.service.ConversationService;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    //userId属于发送消息者
    //msgBody为Json格式，详见消息类型与数据格式，数据类型为text和link
    //cid,在前端页面调用JSAPI唤起联系人会话选择页面，选中后会返回会话cid
    @PostMapping("/sendToConversation")
    public ServiceResult sendToConversation(String userId, String cid, String msgBody, String corpId) throws ApiException{
        OapiMessageSendToConversationResponse rsp = conversationService.sendToConversation(userId, cid, msgBody, corpId);
        return ServiceResult.success(rsp);
    }

    //以上为普通消息通知接口
    //=============================
    //以下为工作通知消息接口



    @PostMapping("/getSendProgress")
    public ServiceResult getSendProgress(Long agentId,Long taskId,String corpId) throws ApiException{
        OapiMessageCorpconversationGetsendprogressResponse rsp = conversationService.getSendProgress(agentId, taskId, corpId);
        return ServiceResult.success(rsp);
    }

    @PostMapping("/getSendResult")
    public ServiceResult getSendResult(Long agentId,Long taskId,String corpId) throws ApiException{
        OapiMessageCorpconversationGetsendresultResponse rsp = conversationService.getSendResult(agentId, taskId, corpId);
        return ServiceResult.success(rsp);
    }
}
