package com.learning.cloud.bill.service.impl;

import com.learning.domain.JsonResult;
import com.learning.cloud.bill.dao.PreBillDao;
import com.learning.cloud.bill.entity.PreBill;
import com.learning.cloud.bill.service.PreBillService;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public JsonResult listPreBill(PreBill preBill) {
        List<PreBill> preBillList = billDao.getByPreBill(preBill);
        return JsonResultUtil.success(new PageEntity<>(preBillList));
    }

    @Override
    public JsonResult getPreBillById(Integer id) {
        PreBill preBill = billDao.getPreBillById(id);
        return JsonResultUtil.success(preBill);
    }

}
