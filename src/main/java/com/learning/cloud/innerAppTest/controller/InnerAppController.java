package com.learning.cloud.innerAppTest.controller;

import com.learning.cloud.innerAppTest.service.InnerAppService;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InnerAppController {
    @Autowired
    private InnerAppService innerAppService;

    @GetMapping("/getToken")
    public ServiceResult getToken() throws ApiException {
        return ServiceResult.success(innerAppService.getToken());
    }

    @GetMapping("/getSchoolDeptIds")
    public ServiceResult getSchoolDeptIds() throws ApiException{
        return ServiceResult.success(innerAppService.getSchoolDeptIds());
    }

}
