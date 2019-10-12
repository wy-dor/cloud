package com.learning.cloud.index.controller;

import com.learning.cloud.index.entity.LoggedRecord;
import com.learning.cloud.index.service.LoggedService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-10-11 16:52
 * @Desc: 登录校园钉记录
 */
@RestController
public class LoggedController {

    @Autowired
    private LoggedService loggedService;

    @PostMapping("/addLoggedRecord")
    public JsonResult addLoggedRecord(LoggedRecord loggedRecord)throws Exception{
        return loggedService.addLoggedRecord(loggedRecord);
    }

    @GetMapping("/refreshSchoolActiveScore")
    public void refreshSchoolActiveScore(){
        loggedService.AddSchoolScoreFormActivity();
    }

}
