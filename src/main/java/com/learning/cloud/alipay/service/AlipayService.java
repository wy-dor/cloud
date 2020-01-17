package com.learning.cloud.alipay.service;

import com.learning.domain.JsonResult;

public interface AlipayService {
    JsonResult sendAliEduBill(Integer preId)throws Exception;

    JsonResult testSendAliEduBill() throws Exception;

    JsonResult testSendAliEduBillOrder()throws Exception;

    // 授权回调令牌
    JsonResult getAuthToken(String app_auth_code, Integer schoolId) throws Exception;
}
