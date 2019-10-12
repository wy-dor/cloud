package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationDimension;
import com.learning.cloud.evaluation.service.EvaluationDimensionService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationDimensionController {
    @Autowired
    private EvaluationDimensionService evaluationDimensionService;

    @PostMapping("/addEvaluationDimension")
    public JsonResult addEvaluationDimension(EvaluationDimension evaluationDimension){
        return evaluationDimensionService.addEvaluationDimension(evaluationDimension);
    }

    @PostMapping("updateEvaluationDimension")
    public JsonResult updateEvaluationDimension(EvaluationDimension evaluationDimension){
        return evaluationDimensionService.updateEvaluationDimension(evaluationDimension);
    }

    @GetMapping("/getEvaluationDimensionById")
    public JsonResult getEvaluationDimensionById(Long id){
        return evaluationDimensionService.getEvaluationDimensionById(id);
    }

    //删除指定评价维度及其下子评价类型
    @GetMapping("/deleteEvaluationDimension")
    public JsonResult deleteEvaluationDimension(Long id){
        return evaluationDimensionService.deleteEvaluationDimension(id);
    }

    @GetMapping("/getEvaluationDimension")
    public JsonResult getEvaluationDimension(EvaluationDimension evaluationDimension){
        return evaluationDimensionService.getEvaluationDimension(evaluationDimension);
    }
}
