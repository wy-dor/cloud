package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationGroup;
import com.learning.domain.JsonResult;

public interface EvaluationGroupService {
    JsonResult addEvaluationGroup(EvaluationGroup evaluationGroup) throws Exception;

    JsonResult getEvaluationGroup(EvaluationGroup evaluationGroup);

    JsonResult getEvaluationGroupById(long id);

    JsonResult deleteEvaluationGroupById(Long id);

    JsonResult updateEvaluationGroup(EvaluationGroup evaluationGroup) throws Exception;

    JsonResult batchUpdateEvaluationGroup(String ids, Integer status);

}
