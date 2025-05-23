package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.CourseDetail;
import com.learning.cloud.course.service.CourseDetailService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-06-12 19:02
 * @Desc: 课表明细
 */
@RestController
public class CourseDetailController {

    @Autowired
    private CourseDetailService courseDetailService;

    @GetMapping("/getCourseDetailByClassId")
    public JsonResult getCourseDetailByClassId(@RequestParam(value = "classId", required = true) Long classId,
                                               @RequestParam(value = "weekDay", required = false) Integer weekDay) throws Exception {
        return courseDetailService.getCourseDetailByClassId(classId, weekDay);
    }

    @PostMapping("/addCourseDetail")
    public JsonResult addCourseDetail(CourseDetail courseDetail) throws Exception {
        return courseDetailService.addCourseDetail(courseDetail);
    }

    @PostMapping("/editCourseDetail")
    public JsonResult editCourseDetail(CourseDetail courseDetail) throws Exception {
        return courseDetailService.editCourseDetail(courseDetail);
    }

    @GetMapping("/getTeacherCourseDetail")
    public JsonResult getTeacherCourseDetail(@RequestParam(value = "teacherId", required = true) String teacherIds,
                                             @RequestParam(value = "weekDay", required = false) Integer weekDay) throws Exception {
        return courseDetailService.getTeacherCourseDetail(teacherIds, weekDay);
    }

    @GetMapping("/getCourseDetailById")
    public JsonResult getCourseDetailById(@RequestParam(value = "id", required = true) Long id) throws Exception {
        return courseDetailService.getCourseDetailById(id);
    }

    //删除课程明细
    @PostMapping("/deleteCourseDetailById")
    public JsonResult deleteCourseDetailById(@RequestParam(value = "id", required = true) Long id) throws Exception {
        return courseDetailService.deleteCourseDetailById(id);
    }

    //清空课表
    @PostMapping("/deleteAllCourseDetail")
    public JsonResult deleteAllCourseDetail(@RequestParam(value = "courseId", required = true) Long courseId) throws Exception {
        return courseDetailService.deleteAllCourseDetail(courseId);
    }

}
