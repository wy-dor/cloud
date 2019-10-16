package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationRecord;
import com.learning.cloud.evaluation.service.EvaluationRecordService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationRecordController {
    @Autowired
    private EvaluationRecordService evaluationRecordService;

    @PostMapping("/addEvaluationRecord")
    public JsonResult addEvaluationRecord(EvaluationRecord evaluationRecord){
        return evaluationRecordService.addEvaluationRecord(evaluationRecord);
    }

    @PostMapping("updateEvaluationRecord")
    public JsonResult updateEvaluationRecord(EvaluationRecord evaluationRecord){
        return evaluationRecordService.updateEvaluationRecord(evaluationRecord);
    }

    @GetMapping("/getEvaluationRecordById")
    public JsonResult getEvaluationRecordById(Long id){
        return evaluationRecordService.getEvaluationRecordById(id);
    }

    //删除指定评价记录方案及其下所有评论内容
    @GetMapping("/deleteEvaluationRecordById")
    public JsonResult deleteEvaluationRecordById(Long id){
        return evaluationRecordService.deleteEvaluationRecordById(id);
    }

    @GetMapping("/getEvaluationRecord")
    public JsonResult getEvaluationRecord(EvaluationRecord evaluationRecord){
        return evaluationRecordService.getEvaluationRecord(evaluationRecord);
    }
}
