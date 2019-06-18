package com.learning.cloud.index.service.impl;

import com.learning.cloud.index.dao.CallbackInfoDao;
import com.learning.cloud.index.entity.CallbackInfo;
import com.learning.cloud.index.service.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class callbackServiceImpl implements CallbackService {

    @Autowired
    private CallbackInfoDao callbackInfoDao;

    @Override
    public String getAuthCodeByCorpId(String corpId) {
        return callbackInfoDao.getAuthCodeByCorpId(corpId);
    }

    @Override
    public int saveCallbackInfo(CallbackInfo callbackInfo) {
        String authCode = callbackInfoDao.getAuthCodeByCorpId(callbackInfo.getAuthCorpId());
        int i = 0;
        if(authCode == null){
            i = callbackInfoDao.insert(callbackInfo);
        }else{
            i = callbackInfoDao.update(callbackInfo);
        }
        return i;
    }
}
