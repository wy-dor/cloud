package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationItem;
import com.learning.domain.JsonResult;

public interface EvaluationItemService {
    JsonResult addEvaluationItem(EvaluationItem evaluationItem) throws Exception;

    JsonResult getEvaluationItem(EvaluationItem evaluationItem);

    JsonResult getEvaluationItemById(Long id);

    JsonResult deleteEvaluationItemById(Long id);

    JsonResult updateEvaluationItem(EvaluationItem evaluationItem) throws Exception;

    JsonResult batchUpdateEvaluationItem(String ids, Integer status);

}
