package com.learning.cloud.bill.service;

import com.learning.domain.JsonResult;
import com.learning.cloud.bill.entity.PreBill;

public interface PreBillService {
    JsonResult addPreBill(PreBill preBill);
}
