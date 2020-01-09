package com.learning.cloud.bill.service;

import com.learning.domain.JsonResult;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bill.entity.BillSearch;

import java.util.List;

public interface BillService {

    JsonResult addAndSendBill(Bill bill)throws Exception;

    JsonResult getSchoolBillInPeriod(BillSearch billSearch);

    JsonResult getSchoolBills(Bill bill);

    void updateCallBackByOrderNo(String tradeNo, String gmtPayment, String totalAmount, String outTradeNo)throws Exception;

    JsonResult addBill(Bill bill);

}
