package com.learning.cloud.courseWare.controller;

import com.learning.cloud.courseWare.entity.CourseWare;
import com.learning.cloud.courseWare.service.CourseWareService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课件
 */
@RestController
public class CourseWareController {

    @Autowired
    private CourseWareService courseWareService;

    @PostMapping("addCourseWare")
    public JsonResult addCourseWare(CourseWare courseWare)throws Exception{
        return courseWareService.addCourseWare(courseWare);
    }

}
