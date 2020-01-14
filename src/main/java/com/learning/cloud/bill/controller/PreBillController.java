package com.learning.cloud.bill.controller;

import com.learning.cloud.bill.entity.PreBill;
import com.learning.cloud.bill.service.PreBillService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PreBillController {
    @Autowired
    private PreBillService preBillService;

    //增加预账单
    @PostMapping("addPreBill")
    public JsonResult addPreBill(@Valid PreBill preBill, BindingResult bindingResult)throws Exception{
        if(bindingResult.hasErrors()){
            return JsonResultUtil.error(0,bindingResult.getFieldError().getDefaultMessage());
        }
        return preBillService.addPreBill(preBill);
    }

    @PostMapping("listPreBill")
    public JsonResult listPreBill(PreBill preBill){
        return preBillService.listPreBill(preBill);
    }

    @GetMapping("/getPreBillById")
    public JsonResult getPreBillById(Integer id){
        return preBillService.getPreBillById(id);
    }

}
