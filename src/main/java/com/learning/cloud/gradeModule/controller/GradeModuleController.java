package com.learning.cloud.gradeModule.controller;

import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.cloud.gradeModule.service.GradeModuleService;
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

    @GetMapping("/getGradeModuleById")
    public JsonResult getGradeModuleById(Long id){
        return gradeModuleService.getGradeModuleById(id);
    }

    @GetMapping("/getAllGradeModule")
    public JsonResult getAllGradeModule(GradeModule gradeModule){
        return gradeModuleService.getAllGradeModule(gradeModule);
    }

    @GetMapping("/deleteGradeModule")
    public JsonResult deleteGradeModule(Long id){
        return gradeModuleService.deleteGradeModule(id);
    }
}
