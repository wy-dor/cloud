package com.learning.cloud.dept.topApi.controller;

import com.learning.cloud.dept.topApi.service.DeptTopApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptTopApiController {
    @Autowired
    private DeptTopApiService deptTopApiService;

}
