package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationRemark;
import com.learning.cloud.evaluation.service.EvaluationRemarkService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationRemarkController {
    @Autowired
    private EvaluationRemarkService remarkService;

    @GetMapping("/getEvaluationRemark")
    public JsonResult getEvaluationRemark(EvaluationRemark evaluationRemark){
        return remarkService.getEvaluationRemark(evaluationRemark);
    }

    @GetMapping("/getEvaluationRemarkById")
    public JsonResult getEvaluationRemarkById(Long id){
        return remarkService.getEvaluationRemarkById(id);
    }

    @PostMapping("/addEvaluationRemark")
    public JsonResult addEvaluationRemark(EvaluationRemark evaluationRemark) throws Exception {
        return remarkService.addEvaluationRemark(evaluationRemark);
    }

    @PostMapping("/updateEvaluationRemark")
    public JsonResult updateEvaluationRemark(EvaluationRemark evaluationRemark)  throws Exception{
        return remarkService.updateEvaluationRemark(evaluationRemark);
    }

    @GetMapping("/deleteEvaluationRemarkById")
    public JsonResult deleteEvaluationRemarkById(Long id){
        return remarkService.deleteEvaluationRemarkById(id);
    }
}

