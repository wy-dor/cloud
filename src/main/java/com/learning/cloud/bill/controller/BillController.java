package com.learning.cloud.bill.controller;

import com.learning.domain.JsonResult;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bill.entity.BillSearch;
import com.learning.cloud.bill.entity.PreBill;
import com.learning.cloud.bill.service.BillService;
import com.learning.cloud.bill.service.PreBillService;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private PreBillService preBillService;

    //新增账单，区分，支付宝和微信账单
    //新增账单，增加prepareBill概念，加入但是还未创建，add后再send
    @PostMapping("addAndSendBill")
    public JsonResult addAndSendBill(@Valid Bill bill, BindingResult bindingResult)throws Exception{
        if(bindingResult.hasErrors()){
            return JsonResultUtil.error(0,bindingResult.getFieldError().getDefaultMessage());
        }
        return billService.addAndSendBill(bill);
    }

    //获取学校某时间段内的收费金额，微信和支付宝
    @GetMapping("getSchoolBillInPeriod")
    public JsonResult getSchoolBillInPeriod(@Valid BillSearch billSearch, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return JsonResultUtil.error(0,bindingResult.getFieldError().getDefaultMessage());
        }
        return billService.getSchoolBillInPeriod(billSearch);
    }

    //获取本校时间段内的所有账单
    @GetMapping("getSchoolBills")
    public JsonResult getSchoolBills(Bill bill){
        return billService.getSchoolBills(bill);
    }

    //新增账单时级联添加学生信息
    @PostMapping("/addBill")
    public JsonResult addBill(Bill bill){
        return billService.addBill(bill);
    }

}
