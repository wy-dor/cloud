package com.learning.cloud.term.controller;

import com.learning.cloud.term.entity.Term;
import com.learning.cloud.term.service.TermService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学期
 */
@RestController
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping("getSchoolTerm")
    public JsonResult getSchoolTerm(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return termService.getSchoolTerm(schoolId);
    }

    @PostMapping("editSchoolTerm")
    public JsonResult editSchoolTerm(Term term)throws Exception{
        return termService.editSchoolTerm(term);
    }
}
