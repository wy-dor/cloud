package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.CourseExchange;
import com.learning.cloud.course.service.CourseExchangeService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-06-13 15:33
 * @Desc: 课程调换
 */
@RestController
public class CourseExchangeController {
    @Autowired
    private CourseExchangeService courseExchangeService;

    @PostMapping("/addCourseExchange")
    public JsonResult addCourseExchange(CourseExchange courseExchange) throws Exception {
        return courseExchangeService.addCourseExchange(courseExchange);
    }

    @GetMapping("/getCourseExchange")
    public JsonResult getCourseExchange(@RequestParam(value = "classId", required = false) Long classId,
                                        @RequestParam(value = "day", required = false) String day) {
        return courseExchangeService.getCourseExchange(classId, day);
    }

    //获取老师未完成的调课
    @GetMapping("/getMyExchange")
    public JsonResult getMyExchange(@RequestParam(value = "teacherId", required = true) Long teacherId, Integer status) throws Exception {
        return courseExchangeService.getMyExchange(teacherId, status);
    }

    @PostMapping("/confirmExchange")
    public JsonResult confirmExchange(CourseExchange courseExchange) throws Exception {
        return courseExchangeService.confirmExchange(courseExchange);
    }

    @GetMapping("/renewCourseExchangeStatus")
    public JsonResult renewCourseExchangeStatus(CourseExchange courseExchange) throws Exception {
        courseExchangeService.renewCourseExchangeStatus(courseExchange);
        return JsonResultUtil.success();
    }

}
