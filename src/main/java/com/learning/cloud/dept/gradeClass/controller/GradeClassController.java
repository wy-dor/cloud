package com.learning.cloud.dept.gradeClass.controller;

import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.gradeClass.service.GradeClassService;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradeClassController {

    @Autowired
    private GradeClassService gradeClassService;

    /*根据老师获取其所任职的班级信息*/
    @GetMapping("/getClassesByTeacher")
    public ServiceResult getClassesByTeacher(Teacher teacher) {
        return gradeClassService.getClassesByTeacher(teacher);
    }

    /*获取学校或分校下的班级列表*/
    /*参数为schoolId或者id(分校)*/
    @GetMapping("/getClassesByCampus")
    public ServiceResult getClassesByCampus(Campus campus){
        return gradeClassService.getClassesByCampus(campus);
    }

    /*获取班级下老师，家长，学生的数量*/
    @GetMapping("/getClassDetails")
    public JsonResult getClassDetails(Integer classId){
        return gradeClassService.getClassDetails(classId);
    }

    /*学校下递进条件查询班级，依次添加查询条件*/
    @GetMapping("/getByGradeClass")
    public JsonResult getByGradeClass(GradeClass gradeClass){
        return gradeClassService.getByGradeClass(gradeClass);
    }

    /*根据班级id获取班级信息，参数id*/
    @GetMapping("/getGradeClassById")
    public JsonResult getGradeClassById(Integer id){
        return gradeClassService.getGradeClassById(id);
    }


}
