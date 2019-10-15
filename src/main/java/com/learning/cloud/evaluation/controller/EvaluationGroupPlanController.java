package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationGroupPlan;
import com.learning.cloud.evaluation.service.EvaluationGroupPlanService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationGroupPlanController {
    @Autowired
    private EvaluationGroupPlanService evaluationGroupPlanService;

    @PostMapping("/addEvaluationGroupPlan")
    public JsonResult addEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan){
        return evaluationGroupPlanService.addEvaluationGroupPlan(evaluationGroupPlan);
    }

    @PostMapping("updateEvaluationGroupPlan")
    public JsonResult updateEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan){
        return evaluationGroupPlanService.updateEvaluationGroupPlan(evaluationGroupPlan);
    }

    @GetMapping("/getEvaluationGroupPlanById")
    public JsonResult getEvaluationGroupPlanById(Long id){
        return evaluationGroupPlanService.getEvaluationGroupPlanById(id);
    }

    //删除指定分组方案及其下分组
    @GetMapping("/deleteEvaluationGroupPlanById")
    public JsonResult deleteEvaluationGroupPlanById(Long id){
        return evaluationGroupPlanService.deleteEvaluationGroupPlanById(id);
    }

    @GetMapping("/getEvaluationGroupPlan")
    public JsonResult getEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan){
        return evaluationGroupPlanService.getEvaluationGroupPlan(evaluationGroupPlan);
    }
}
