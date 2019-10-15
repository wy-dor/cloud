package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationIcon;
import com.learning.cloud.evaluation.service.EvaluationIconService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EvaluationIconController {
    @Autowired
    private EvaluationIconService evaluationIconService;

    @GetMapping("/getEvaluationIcon")
    public JsonResult getEvaluationIcon(EvaluationIcon evaluationIcon){
        return evaluationIconService.getEvaluationIcon(evaluationIcon);
    }

    @GetMapping("/getEvaluationIconById")
    public JsonResult getEvaluationIconById(long id){
        return evaluationIconService.getEvaluationIconById(id);
    }

    @PostMapping("/addEvaluationIcon")
    public JsonResult addEvaluationIcon(@RequestParam(value="file",required = false)MultipartFile file, EvaluationIcon evaluationIcon) throws Exception {
        return evaluationIconService.addEvaluationIcon(file, evaluationIcon);
    }

    @PostMapping("/updateEvaluationIcon")
    public JsonResult updateEvaluationIcon(EvaluationIcon evaluationIcon)  throws Exception{
        return evaluationIconService.updateEvaluationIcon(evaluationIcon);
    }

    @GetMapping("/deleteEvaluationIconById")
    public JsonResult deleteEvaluationIconById(Long id){
        return evaluationIconService.deleteEvaluationIconById(id);
    }
}

