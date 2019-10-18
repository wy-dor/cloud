package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.Course;
import com.learning.cloud.course.service.CourseService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-06-11 16:48
 * @Desc: 课程
 */
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    //课程主表
    @GetMapping("/getCourseByClassId")
    public JsonResult getCourseByClassId(@RequestParam(value="classId",required = true) Long classId)throws Exception{
        return courseService.getCourseByClassId(classId);
    }

    //班级课表
    @PostMapping("/addCourse")
    public JsonResult addCourse(Course course)throws Exception{
        return courseService.addCourse(course);
    }

    //修改课表
    @PostMapping("/editCourse")
    public JsonResult editCourse(Course course) throws Exception{
        return courseService.editCourse(course);
    }

    @GetMapping("/getSchoolCourse")
    public JsonResult getSchoolCourse(Course course)throws Exception{
        return courseService.getSchoolCourse(course);
    }

    @PostMapping("/publishCourse")
    public JsonResult publishCourse(@RequestParam(value="id",required = true) Long id) throws Exception{
        return courseService.publishCourse(id);
    }

}
