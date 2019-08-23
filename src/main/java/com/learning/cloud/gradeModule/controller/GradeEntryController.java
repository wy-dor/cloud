package com.learning.cloud.gradeModule.controller;

import com.learning.cloud.gradeModule.entity.GradeEntry;
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
    
    @PostMapping("/addGradeEntry")
    public JsonResult addGradeEntry(GradeEntry gradeEntry){
        return gradeEntryService.addGradeEntry(gradeEntry);
    }

    @GetMapping("/getGradeEntryById")
    public JsonResult getGradeEntryById(Long id){
        return gradeEntryService.getGradeEntryById(id);
    }

    @GetMapping("/getAllGradeEntry")
    public JsonResult getAllGradeEntry(GradeEntry gradeEntry){
        return gradeEntryService.getAllGradeEntry(gradeEntry);
    }
}
