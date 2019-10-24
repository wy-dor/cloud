package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationGroup;
import com.learning.domain.JsonResult;

import java.util.List;

public interface EvaluationGroupService {
    JsonResult addEvaluationGroup(EvaluationGroup evaluationGroup) throws Exception;

    List<EvaluationGroup> getEvaluationGroup(EvaluationGroup evaluationGroup);

    JsonResult getEvaluationGroupById(Long id);

    JsonResult deleteEvaluationGroupById(Long id);

    JsonResult updateEvaluationGroup(EvaluationGroup evaluationGroup) throws Exception;

    JsonResult batchUpdateEvaluationGroup(String ids, Integer status);

}
