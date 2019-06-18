package com.learning.cloud.index.service;

import com.learning.cloud.index.entity.CallbackInfo;

public interface CallbackService {

    String getAuthCodeByCorpId(String corpId);

    int saveCallbackInfo(CallbackInfo callbackInfo);

}
