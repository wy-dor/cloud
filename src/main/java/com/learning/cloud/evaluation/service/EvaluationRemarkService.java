package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationRemark;
import com.learning.domain.JsonResult;

public interface EvaluationRemarkService {
    JsonResult addEvaluationRemark(EvaluationRemark evaluationRemark) throws Exception;

    JsonResult getEvaluationRemark(EvaluationRemark evaluationRemark);

    JsonResult getEvaluationRemarkById(Long id);

    JsonResult deleteEvaluationRemarkById(Long id);

    JsonResult updateEvaluationRemark(EvaluationRemark evaluationRemark) throws Exception;
}
