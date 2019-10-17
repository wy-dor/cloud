package com.learning.cloud.evaluation.service;

import com.learning.cloud.evaluation.entity.EvaluationIcon;
import com.learning.domain.JsonResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public interface EvaluationIconService {
    JsonResult addEvaluationIcon(MultipartFile file, EvaluationIcon evaluationIcon) throws Exception;

    JsonResult getEvaluationIcon(EvaluationIcon evaluationIcon);

    EvaluationIcon getEvaluationIconById(Long id);

    JsonResult deleteEvaluationIconById(Long id);

    JsonResult updateEvaluationIcon(EvaluationIcon evaluationIcon) throws Exception;

}
