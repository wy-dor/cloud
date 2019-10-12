package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationItem;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface EvaluationItemService {
    JsonResult addEvaluationItem(MultipartFile file, EvaluationItem evaluationItem) throws Exception;

    JsonResult getEvaluationItem(EvaluationItem evaluationItem);

    JsonResult getEvaluationItemById(long id);

    JsonResult deleteEvaluationItemById(Long id);

    JsonResult updateEvaluationItem(MultipartFile file, EvaluationItem evaluationItem) throws Exception;

    JsonResult batchUpdateEvaluationItem(String ids, Integer status);

}
