package com.learning.cloud.gradeModule.controller;

import com.learning.cloud.gradeModule.entity.GradeEntry;
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

    @PostMapping("/saveGradeModule")
    public JsonResult saveGradeModule(GradeModule gradeModule){
        return gradeModuleService.saveGradeModule(gradeModule);
    }

    @GetMapping("/getGradeModuleById")
    public JsonResult getGradeModuleById(Long id){
        return gradeModuleService.getGradeModuleById(id);
    }

    @GetMapping("/getAllGradeModule")
    public JsonResult getAllGradeModule(GradeModule gradeModule){
        return gradeModuleService.getAllGradeModule(gradeModule);
    }

    @GetMapping("/getAllGradeModuleForAdministrator")
    public JsonResult getAllGradeModuleForAdministrator(GradeModule gradeModule){
        return gradeModuleService.getAllGradeModuleForAdministrator(gradeModule);
    }

    @GetMapping("/deleteGradeModule")
    public JsonResult deleteGradeModule(Long id){
        return gradeModuleService.deleteGradeModule(id);
    }

    @GetMapping("/getGradeModulesForClass")
    public JsonResult getGradeModulesForClass(GradeEntry gradeEntry){
        return gradeModuleService.getGradeModulesForClass(gradeEntry);
    }

    @GetMapping("/updateGradeModule")
    public JsonResult updateGradeModule(GradeModule gradeModule){
        return gradeModuleService.updateGradeModule(gradeModule);
    }
}
