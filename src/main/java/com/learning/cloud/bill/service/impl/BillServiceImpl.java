package com.learning.cloud.bill.service.impl;

import com.learning.cloud.alipay.service.AlipayService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.enums.JsonResultEnum;
import com.learning.cloud.bill.dao.BillDao;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bill.entity.BillSearch;
import com.learning.cloud.bill.entity.SumBillType;
import com.learning.cloud.bill.service.BillService;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDao billDao;

    @Autowired
    private AlipayService alipayService;

    //新增账单，严格验证
    @Override
    public JsonResult addAndSendBill(Bill bill) throws Exception {
        String orderNo = "";
        //账单类型1-支付宝当面付，2-微信当面付，3-支付宝教育缴费
        int i = billDao.addBill(bill);

        return JsonResultUtil.success(orderNo);
    }

    //获取学校时间段内的缴费金额
    @Override
    public JsonResult getSchoolBillInPeriod(BillSearch billSearch) {
        List<SumBillType> sumBillTypes = billDao.getSchoolBillInPeriod(billSearch);
        return JsonResultUtil.success(sumBillTypes);
    }

    //获取学校的账单列表
    @Override
    public JsonResult getSchoolBills(Bill bill) {
        List<Bill> bills = billDao.getSchoolBills(bill);
        return JsonResultUtil.success(new PageEntity<>(bills));
    }

    // 支付宝回调更新账单状态
    @Override
    public void updateCallBackByOrderNo(String tradeNo, String gmtPayment, String totalAmount, String outTradeNo) throws Exception{
        billDao.updateCallBackByOrderNo(tradeNo, gmtPayment, totalAmount, outTradeNo);
    }

    //增加账单
    @Override
    public JsonResult addBill(Bill bill) {
        int i = billDao.addBill(bill);
        if (i == 0) {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
        billDao.updateBillStateById("SUCCESS",bill.getId());
        return JsonResultUtil.success(bill.getStudentId());
    }
}
