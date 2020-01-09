package com.learning.cloud.bill.service;

import com.learning.domain.JsonResult;
import com.learning.cloud.bill.entity.ParentBill;

public interface ParentBillService {
    JsonResult deleteParentBill(Integer id)throws Exception;

    JsonResult getParentBill(ParentBill parentBill);

    JsonResult sendBill(Integer id) throws Exception;

    JsonResult addParentBill(ParentBill parentBill);
}
