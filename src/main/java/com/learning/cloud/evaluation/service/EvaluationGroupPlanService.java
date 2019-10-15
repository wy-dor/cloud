package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationGroupPlan;
import com.learning.domain.JsonResult;

public interface EvaluationGroupPlanService {
    JsonResult addEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan);

    JsonResult getEvaluationGroupPlanById(Long id);

    JsonResult deleteEvaluationGroupPlanById(Long id);

    JsonResult updateEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan);

    JsonResult getEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan);
}
