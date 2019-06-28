package com.learning.cloud.user.teacher.controller;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.user.teacher.service.TeacherService;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /*根据用户id获取老师信息*/
    @GetMapping("/getByUserId")
    public ServiceResult getByUserId(String userId) {
        return teacherService.getByUserId(userId);
    }

    /*获取指定班级的老师数量*/
    @GetMapping("/getClassTeacherNum")
    public JsonResult getClassTeacherNum(Integer classId){
        return teacherService.getClassTeacherNum(classId);
    }

    /*获取指定班级下的老师列表*/
    /*参数为班级id*/
    @GetMapping("/getClassTeachers")
    public JsonResult getClassTeachers(GradeClass gradeClass){
        return teacherService.getClassTeachers(gradeClass);
    }

    /*设置老师的所教科目*/
    /*参数为老师的id和campusType*/
    @GetMapping("/setTeacherCourseType")
    public JsonResult setTeacherCourseType(Teacher teacher){
        return teacherService.setTeacherCourseType(teacher);
    }

}
