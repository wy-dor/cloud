package com.learning.cloud.school.controller;

import com.learning.cloud.school.entity.PaySchool;
import com.learning.cloud.school.service.PaySchoolService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: yyt
 * @Date: 2018-12-31 19:56
 * @Desc:
 */
@RestController
public class PaySchoolController {

    @Autowired
    private PaySchoolService paySchoolService;

    //新增学校
    @PostMapping("/addPaySchool")
    public JsonResult addPaySchool(@Valid PaySchool school, BindingResult bindingResult)throws Exception{
        if(bindingResult.hasErrors()){
            return JsonResultUtil.error(0,bindingResult.getFieldError().getDefaultMessage());
        }
        return paySchoolService.addPaySchool(school);
    }

    //修改学校
    @PostMapping("/updatePaySchool")
    public JsonResult updatePaySchool(PaySchool school)throws Exception{
        return paySchoolService.updatePaySchool(school);
    }

    //兼容二维码
    @GetMapping("getPaySchoolIdByNo")
    public JsonResult getPaySchoolIdByNo(@RequestParam(value="schoolNo",required = true) String schoolNo){
        return paySchoolService.getPaySchoolIdByNo(schoolNo);
    }

    //获取学校的二维码地址
    @GetMapping("getPaySchoolQrCodeUrl")
    public JsonResult getPaySchoolQrCodeUrl(Integer schoolId,Integer campusId)throws Exception{
        return paySchoolService.getPaySchoolQrCodeUrl(schoolId, campusId);
    }

    //上传学校信息给支付宝
    @GetMapping("getPaySchoolNoFromAli")
    public JsonResult getPaySchoolNoFromAli(Integer schoolId)throws Exception{
        return paySchoolService.getPaySchoolNoFromAli(schoolId);
    }

    //根据学校id获取学校信息
    @GetMapping("getPaySchoolById")
    public JsonResult getPaySchoolById(Integer schoolId){
        return paySchoolService.getPaySchoolById(schoolId);
    }

    //获取时间段内支付学校成功的账单
    //需要参数为id,startTime,endTime,campusId(有分校则传入，无分校则不需要传入)
    //返回billList,两个字段agentName,schoolName加Bill
    @GetMapping("/getPaySchoolBillsInTime")
    public JsonResult getPaySchoolBillsInTime(PaySchool school) {
        return paySchoolService.getPaySchoolBillsInTime(school);
    }

    //获取学校时间段内支付成功的账单统计
    //主要参数为school的"id","startTime","endTime",返回amountItems
    @GetMapping("/getPaySchoolTotalBillAmount")
    public JsonResult getPaySchoolTotalBillAmount(PaySchool school) {
        return paySchoolService.getPaySchoolTotalBillAmount(school);
    }

    //获取指定月份的学校账单统计
    //参数需要month(格式为2019-05)，id
    //返回amountItems
    @GetMapping("/getPaySchoolMonthlyBills")
    public JsonResult getPaySchoolMonthlyBills(PaySchool school){
        return paySchoolService.getPaySchoolMonthlyBills(school);
    }

    //获取指定月份的学校账单
    //参数需要month(格式为2019-05)，id
    //返回billList，List<Bill>类型
    @GetMapping("/getPaySchoolBillsByMonth")
    public JsonResult getPaySchoolBillsByMonth(PaySchool school){
        return paySchoolService.getPaySchoolBillsByMonth(school);
    }

    //更新k12授权状态
    @GetMapping("/updateAuthK12")
    public JsonResult updateAuthK12(Integer schoolId){
        return paySchoolService.updateAuthK12(schoolId);
    }
}
