package com.learning.cloud.ding.onlineSign.controller;

import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.ding.onlineSign.service.SignService;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SignController {

    @Autowired
    private SignService signService;

    /*发布签字任务*/
    @PostMapping("/addSignTask")
    public JsonResult addSignTask(Sign sign)throws Exception{
        return signService.addSignTask(sign);
    }

    /*签字*/
    @GetMapping("/signName")
    public JsonResult signName(SignRecord signRecord)throws Exception{
        return signService.signName(signRecord);
    }

    /*获取指定班级下所有签名任务，state为0时任务已失效*/
    @GetMapping("/getAllTasks")
    public JsonResult getAllTasks(Parent parent)throws Exception{
        return signService.getAllTasks(parent);
    }

    /*获取有效签字列表*/
    @GetMapping("/getValidTaskList")
    public JsonResult getValidTaskList(Parent parent) throws Exception {
        return signService.getValidTaskList(parent);
    }

    /*获取家长未完成签字列表*/
    /*主要参数classId和userId*/
    @GetMapping("/getUnsignedTasks")
    public JsonResult getUnsignedTasks(Parent parent) throws Exception {
        return signService.getUnsignedTasks(parent);
    }

    /*老师关闭签字任务*/
    @GetMapping("/setStateInvalid")
    public JsonResult setStateInvalid(Integer signId) throws Exception {
        return signService.setStateInvalid(signId);
    }

    /*获取每个签名任务需要签名的数量*/
    @GetMapping("/getSignNum")
    public JsonResult getSignNum(Integer signId) throws Exception{
        return  signService.getSignNum(signId);
    }

    /*获取签名任务下已签名数量*/
    @GetMapping("/getRecordCount")
    public JsonResult getRecordCount(Integer signId) throws Exception{
        return signService.getRecordCount(signId);
    }

    /*获取签名任务下已签名的签名记录*/
    @GetMapping("/getRecordsBySignId")
    public JsonResult getRecordsBySignId(Sign sign) throws Exception{
        return signService.getRecordsBySignId(sign);
    }
}
