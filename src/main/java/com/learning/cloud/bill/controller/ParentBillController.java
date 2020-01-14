package com.learning.cloud.bill.controller;

import com.learning.domain.JsonResult;
import com.learning.cloud.bill.entity.ParentBill;
import com.learning.cloud.bill.service.ParentBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParentBillController {

    @Autowired
    private ParentBillService parentBillService;

    @GetMapping("/getParentBillById")
    public JsonResult getParentBillById(Integer id){
        return parentBillService.getParentBillById(id);
    }

    @PostMapping("/deleteParentBill")
    public JsonResult deleteParentBill(Integer id)throws Exception{
        //删除父账单，先删除子账单
        return parentBillService.deleteParentBill(id);
    }

    @GetMapping("/getParentBill")
    public JsonResult getParentBill(ParentBill parentBill){
        return parentBillService.getParentBill(parentBill);
    }

    //发送账单
    @PostMapping("/sendBill")
    public JsonResult sendBill(Integer id) throws Exception{
        return parentBillService.sendBill(id);
    }

    //手机增加账单
    @PostMapping("/addParentBill")
    public JsonResult addParentBill(ParentBill parentBill) throws Exception {
        return parentBillService.addParentBill(parentBill);
    }
}
