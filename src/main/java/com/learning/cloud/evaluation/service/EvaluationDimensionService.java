package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationDimension;
import com.learning.domain.JsonResult;

public interface EvaluationDimensionService {
    JsonResult addEvaluationDimension(EvaluationDimension evaluationDimension);

    JsonResult getEvaluationDimensionById(Long id);

    JsonResult deleteEvaluationDimensionById(Long id);

    JsonResult updateEvaluationDimension(EvaluationDimension evaluationDimension);

    JsonResult getEvaluationDimension(EvaluationDimension evaluationDimension);
}
