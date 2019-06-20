package com.learning.cloud.bureau.controller;

import com.learning.cloud.bureau.service.BureauService;
import com.learning.cloud.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BureauController {

    @Autowired
    public BureauService bureauService;

    @GetMapping("/getBureaus")
    public ServiceResult getBureaus(){
        return ServiceResult.success(bureauService.getBureaus());
    }
}
