package com.learning.cloud.alipay.controller;

import com.learning.cloud.alipay.service.AlipayService;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bill.entity.ParentBill;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlipayController {
    @Autowired
    private AlipayService alipayService;

    @PostMapping("/sendAliEduBill")
    public JsonResult sendAliEduBill(Bill bill, ParentBill parentBill) throws Exception {
        return alipayService.sendAliEduBill(bill,parentBill);
    }

    @PostMapping("/testSendAliEduBill")
    public JsonResult testSendAliEduBill() throws Exception {
        return alipayService.testSendAliEduBill();
    }

    @PostMapping("/testSendAliEduBillOrder")
    public JsonResult testSendAliEduBillOrder() throws Exception {
        return alipayService.testSendAliEduBillOrder();
    }
}
