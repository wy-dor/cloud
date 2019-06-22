package com.learning.cloud.ding.onlineSign.controller;

import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.ding.onlineSign.service.SignService;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /*获取有效签字列表*/
    @GetMapping("/getValidTaskList")
    public JsonResult getValidTaskList(Parent parent) throws Exception {
        return signService.getValidTaskList(parent);
    }

    /*获取家长未完成签字列表*/
    @GetMapping("/getUndoneTaskList")
    public JsonResult getUndoneTaskList(Parent parent) throws Exception {
        return signService.getUndoneTaskList(parent);
    }

    @GetMapping("/setSateInvalid")
    public JsonResult setSateInvalid(Integer signId) throws Exception {
        return signService.setSateInvalid(signId);
    }
}
