package com.learning.cloud.gradeModule.controller;

import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeEntryJsonStr;
import com.learning.cloud.gradeModule.service.GradeEntryService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradeEntryController {
    
    @Autowired
    private GradeEntryService gradeEntryService;
    
    @PostMapping("/saveGradeEntry")
    public JsonResult saveGradeEntry(GradeEntryJsonStr gradeEntryJsonStr){
        return gradeEntryService.saveGradeEntry(gradeEntryJsonStr);
    }

    @GetMapping("/getGradeEntryById")
    public JsonResult getGradeEntryById(Long id){
        return gradeEntryService.getGradeEntryById(id);
    }

    @GetMapping("/getByGradeEntry")
    public JsonResult getByGradeEntry(GradeEntry gradeEntry){
        return gradeEntryService.getByGradeEntry(gradeEntry);
    }

    @GetMapping("/getGradeEntryStatistics")
    public JsonResult getGradeEntryStatistics(GradeEntry gradeEntry){
        return gradeEntryService.getGradeEntryStatistics(gradeEntry);
    }

    @GetMapping("/getGradeEntryForStudent")
    public JsonResult getGradeEntryForStudent(Long moduleId, String userId){
        return gradeEntryService.getGradeEntryForStudent(moduleId,userId);
    }

}
