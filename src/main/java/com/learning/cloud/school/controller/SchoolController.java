package com.learning.cloud.school.controller;

import com.learning.cloud.school.entity.School;
import com.learning.cloud.school.service.SchoolService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    //根据corpId获取schoolId
    @GetMapping("/getSchoolIdByCorpId")
    public JsonResult getSchoolIdByCorpId(String corpId){
        return schoolService.getSchoolIdByCorpId(corpId);
    }

    @GetMapping("/getBySchool")
    public JsonResult getBySchool(School school){
        return schoolService.getBySchool(school);
    }
}
