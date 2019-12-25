package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.TeacherCourse;
import com.learning.cloud.course.service.TeacherCourseService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yyt
 * @Date: 2019/11/11 10:15 上午
 * @Desc:
 */
@RestController
public class TeacherCourseController {

    @Autowired
    private TeacherCourseService teacherCourseService;

    @GetMapping("/getTeacherCourseTemplet")
    public JsonResult getTeacherCourseTemplet(Long schoolId) throws Exception {
        return teacherCourseService.getTeacherCourseTemplet(schoolId);
    }

    //导入excel
    @PostMapping("/importTeacherCourse")
    public JsonResult importTeacherCourse(@RequestParam("file") MultipartFile file, @RequestParam("schoolId") Long schoolId) throws Exception {
        return teacherCourseService.importTeacherCourse(file, schoolId);
    }

}
