package com.learning.cloud.conversation.service;

import com.dingtalk.api.response.OapiMessageCorpconversationGetsendprogressResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendresultResponse;
import com.dingtalk.api.response.OapiMessageSendToConversationResponse;
import com.taobao.api.ApiException;

public interface ConversationService {

    OapiMessageSendToConversationResponse sendToConversation(String userId, String cid, String msgBody, String corpId) throws ApiException;

    //以上为普通消息通知接口
    //=============================
    //以下为工作通知消息接口

    OapiMessageCorpconversationGetsendprogressResponse getSendProgress(Long agentId, Long taskId, String corpId) throws ApiException;

    OapiMessageCorpconversationGetsendresultResponse getSendResult(Long agentId, Long taskId, String corpId) throws ApiException;
}
