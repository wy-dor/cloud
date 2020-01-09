package com.learning.cloud.alipay.controller;

import com.learning.domain.JsonResult;
import com.learning.cloud.alipay.service.AliBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *账单状态查询，同步接口
 */
@RestController
public class AliBill {

    @Autowired
    private AliBillService aliBillService;

    @GetMapping("/getBillState")
    public JsonResult getBillState(Integer id)throws Exception{
        return aliBillService.getBillState(id);
    }

    // 确认缴费成功
    @PostMapping("/modifyBillState")
    public JsonResult modifyBillState(Integer id, Integer status)throws Exception{
        return aliBillService.modifyBillState(id, status);
    }

}
