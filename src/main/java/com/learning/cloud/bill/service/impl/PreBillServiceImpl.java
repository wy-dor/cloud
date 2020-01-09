package com.learning.cloud.bill.service.impl;

import com.learning.domain.JsonResult;
import com.learning.cloud.bill.dao.PreBillDao;
import com.learning.cloud.bill.entity.PreBill;
import com.learning.cloud.bill.service.PreBillService;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PreBillServiceImpl implements PreBillService {

    @Autowired
    private PreBillDao billDao;

    //新增账单，严格验证
    @Override
    public JsonResult addPreBill(PreBill preBill) {
        int i = billDao.addPreBill(preBill);
        return JsonResultUtil.success();
    }

}
