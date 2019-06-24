package com.learning.cloud.user.parent.controller;

import com.learning.cloud.user.parent.service.ParentService;
import com.learning.cloud.util.ServiceResult;
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

}
