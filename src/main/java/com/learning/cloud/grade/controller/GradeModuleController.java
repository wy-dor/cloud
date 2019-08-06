package com.learning.cloud.grade.controller;

import com.learning.cloud.grade.entity.GradeModule;
import com.learning.cloud.grade.service.GradeModuleService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradeModuleController {

    @Autowired
    private GradeModuleService gradeModuleService;

    @PostMapping("/addGradeModule")
    public JsonResult addGradeModule(GradeModule gradeModule){
        return gradeModuleService.addGradeModule(gradeModule);
    }

    @GetMapping("/getById")
    public JsonResult getById(Integer id){
        return gradeModuleService.getById(id);
    }
}
