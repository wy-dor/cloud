package com.learning.cloud.alipay.service;

import com.learning.domain.JsonResult;

public interface AliBillService {
    JsonResult getBillState(Integer id)throws Exception;

    JsonResult modifyBillState(Integer id, Integer status)throws Exception;

    void modifyBillStateInCallBack(String outTradeNo)throws Exception;
}
