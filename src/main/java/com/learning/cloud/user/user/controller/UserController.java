package com.learning.cloud.user.user.controller;

import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/getUserByUserId")
    public JsonResult getUserByUserId(String userId) {
        User byUserId = userDao.getByUserId(userId);
        return JsonResultUtil.success(byUserId);
    }
}
