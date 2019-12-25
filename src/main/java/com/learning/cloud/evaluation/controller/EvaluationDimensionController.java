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
    private EvaluationDimensionService dimensionService;

    @PostMapping("/addEvaluationDimension")
    public JsonResult addEvaluationDimension(EvaluationDimension evaluationDimension) {
        return dimensionService.addEvaluationDimension(evaluationDimension);
    }

    @PostMapping("updateEvaluationDimension")
    public JsonResult updateEvaluationDimension(EvaluationDimension evaluationDimension) {
        return dimensionService.updateEvaluationDimension(evaluationDimension);
    }

    @GetMapping("/getEvaluationDimensionById")
    public JsonResult getEvaluationDimensionById(Long id) {
        return dimensionService.getEvaluationDimensionById(id);
    }

    //删除指定评价维度及其下子评价类型
    @GetMapping("/deleteEvaluationDimensionById")
    public JsonResult deleteEvaluationDimensionById(Long id) {
        return dimensionService.deleteEvaluationDimensionById(id);
    }

    @GetMapping("/getEvaluationDimension")
    public JsonResult getEvaluationDimension(EvaluationDimension evaluationDimension) {
        return dimensionService.getEvaluationDimension(evaluationDimension);
    }
}
