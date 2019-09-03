package com.learning.cloud.innerApp.controller;

import com.learning.cloud.innerApp.service.InnerAppService;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class InnerAppController {
    @Autowired
    private InnerAppService innerAppService;

    @Autowired
    private UserDao userDao;

    @GetMapping("/innerAppLogin")
    public ServiceResult innerAppLogin(String authCode) throws ApiException {
        Map<String,Object> map = new HashMap<>();
        Integer schoolId = null;
        String accessToken = innerAppService.getToken();
        String userId = innerAppService.getUserId(accessToken,authCode);
        map.put("userId",userId);
        //todo
        //        //userId是否会出现对应多个schoolId的情况
        //        //若出现如何处理数据
        User user = userDao.getByUserId(userId);
        if(user != null){
            schoolId = user.getSchoolId();
            map.put("schoolId",schoolId);
        }
        return ServiceResult.success(map);
    }

    @GetMapping("/getToken")
    public ServiceResult getToken() throws ApiException {
        return ServiceResult.success(innerAppService.getToken());
    }

    @GetMapping("/getSchoolDeptIds")
    public ServiceResult getSchoolDeptIds() throws ApiException{
        return ServiceResult.success(innerAppService.getSchoolDeptIds());
    }

}
