package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationRecord;
import com.learning.domain.JsonResult;

public interface EvaluationRecordService {
    JsonResult addEvaluationRecord(EvaluationRecord evaluationRecord);

    JsonResult getEvaluationRecordById(Long id);

    JsonResult deleteEvaluationRecordById(Long id);

    JsonResult updateEvaluationRecord(EvaluationRecord evaluationRecord);

    JsonResult getEvaluationRecord(EvaluationRecord evaluationRecord);
}
