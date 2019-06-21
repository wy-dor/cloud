package com.learning.cloud.school.controller;

import com.learning.cloud.school.entity.School;
import com.learning.cloud.school.service.SchoolService;
import com.learning.cloud.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolController {

    @Autowired
    public SchoolService schoolService;

    /*关联school与bureau*/
    /*传入schoolId和bureauId*/
    @GetMapping("/setBureauId")
    public ServiceResult setBureauId(School school){
        return schoolService.setBureauId(school);
    }

}
