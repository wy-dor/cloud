package com.learning.cloud.alipay.service;

import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bill.entity.ParentBill;
import com.learning.domain.JsonResult;

public interface AlipayService {
    JsonResult sendAliEduBill(Bill bill, ParentBill parentBill)throws Exception;

    JsonResult testSendAliEduBill() throws Exception;
}
