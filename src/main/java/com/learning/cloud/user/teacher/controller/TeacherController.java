package com.learning.cloud.user.teacher.controller;

import com.learning.cloud.user.teacher.service.TeacherService;
import com.learning.cloud.util.ServiceResult;
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

}
