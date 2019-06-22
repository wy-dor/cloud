package com.learning.cloud.dept.gradeClass.controller;

import com.learning.cloud.dept.gradeClass.service.GradeClassService;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.user.teacher.service.TeacherService;
import com.learning.cloud.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradeClassController {

    @Autowired
    public GradeClassService gradeClassService;

    /*根据老师获取其所任职的班级信息*/
    @GetMapping("/getClassesByTeacher")
    public ServiceResult getClassesByTeacher(Teacher teacher) {
        return gradeClassService.getClassesByTeacher(teacher);
    }
}
