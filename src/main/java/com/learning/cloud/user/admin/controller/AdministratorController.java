package com.learning.cloud.user.admin.controller;

import com.learning.cloud.user.admin.service.AdministratorService;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/getRoleList")
    public ServiceResult getRoleList(String accessToken) throws ApiException {
        return ServiceResult.success(administratorService.getRoleList(accessToken));
    }

    @GetMapping("/getRoleSimpleList")
    public ServiceResult getRoleSimpleList(long roleId, String accessToken) throws ApiException{
        return administratorService.getRoleSimpleList(roleId,accessToken);
    }

}
