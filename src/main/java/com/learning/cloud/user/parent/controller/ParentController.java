package com.learning.cloud.user.parent.controller;

import com.learning.cloud.user.parent.service.ParentService;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParentController {

    @Autowired
    private ParentService parentService;

    /*根据用户id获取家长信息*/
    @GetMapping("/getParentByUserId")
    public ServiceResult getParentByUserId(String userId) {
        return parentService.getByUserId(userId);
    }

    /*获取指定班级的家长数量*/
    @GetMapping("/getClassParentNum")
    public JsonResult getClassParentNum(Integer classId){
        return parentService.getClassParentNum(classId);
    }

}
