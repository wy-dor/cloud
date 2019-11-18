package com.learning.cloud.workProcess.controller;

import com.learning.cloud.workProcess.entity.CourseInstance;
import com.learning.cloud.workProcess.service.CourseExchangeProcessService;
import com.learning.cloud.workProcess.service.ProcessService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019/11/14 9:49 上午
 * @Desc:
 */
@RestController
public class CourseExchangeProcessController {

    @Autowired
    private CourseExchangeProcessService courseExchangeProcessService;

    @PostMapping("/createCourseExchangeProcess")
    public JsonResult createCourseExchangeProcess(String corpId)throws Exception{
        //创建固定格式，填充对应字段值
        return courseExchangeProcessService.createCourseExchangeProcess(corpId);
    }

    @PostMapping("/createCourseExchangeProcessInstance")
    public JsonResult createCourseExchangeProcessInstance(CourseInstance courseInstance)throws Exception{
        return courseExchangeProcessService.createCourseExchangeProcessInstance(courseInstance);
    }

    //获取学校的流程模板
    @GetMapping("/getCourseProcess")
    public JsonResult getCourseProcess(String corpId)throws Exception{
        return courseExchangeProcessService.getCourseProcess(corpId);

    }
}
