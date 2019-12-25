package com.learning.cloud.user.student.controller;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.user.student.entity.Student;
import com.learning.cloud.user.student.service.StudentService;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /*根据用户id获取学生信息*/
    @GetMapping("/getStudentByUserId")
    public ServiceResult getStudentByUserId(String userId) {
        return studentService.getByUserId(userId);
    }

    /*获取指定班级的学生数量*/
    @GetMapping("/getClassStudentNum")
    public JsonResult getClassStudentNum(Integer classId) {
        return studentService.getClassStudentNum(classId);
    }

    /*获取指定班级下的学生列表*/
    @GetMapping("/getClassStudents")
    public JsonResult getClassStudents(GradeClass gradeClass) {
        return studentService.getClassStudents(gradeClass);
    }

    /*对学生姓名进行模糊查询*/
    @GetMapping("/getStudentsByName")
    public JsonResult getStudentsByName(Student student) {
        return studentService.getStudentsByName(student);
    }


}


