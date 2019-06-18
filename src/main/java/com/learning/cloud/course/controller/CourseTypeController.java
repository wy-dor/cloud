package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.CourseType;
import com.learning.cloud.course.service.CourseTypeService;
import com.learning.domain.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-06-10 15:58
 * @Desc: 各个学校的课程类型
 */
@RestController
public class CourseTypeController {

    @Autowired
    private CourseTypeService courseTypeService;

    @GetMapping("getSchoolCourseType")
    public JsonResult getSchoolCourseType(@RequestParam(value="schoolId",required = true) Long schoolId) throws Exception{
        return courseTypeService.getSchoolCourseType(schoolId);
    }

    @PostMapping("addSchoolCourseType")
    public JsonResult addSchoolCourseType(CourseType courseType) throws Exception{
        return courseTypeService.addSchoolCourseType(courseType);
    }

    @GetMapping("deleteCourseType")
    public JsonResult deleteCourseType(@RequestParam(value="id",required = true) Long id) throws Exception{
        return courseTypeService.deleteCourseType(id);
    }


}
