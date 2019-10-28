package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationRecord;
import com.learning.cloud.evaluation.service.EvaluationRecordService;
import com.learning.cloud.user.student.entity.Student;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationRecordController {
    @Autowired
    private EvaluationRecordService recordService;

    @PostMapping("/addEvaluationRecord")
    public JsonResult addEvaluationRecord(EvaluationRecord evaluationRecord){
        return recordService.addEvaluationRecord(evaluationRecord);
    }

    @PostMapping("updateEvaluationRecord")
    public JsonResult updateEvaluationRecord(EvaluationRecord evaluationRecord){
        return recordService.updateEvaluationRecord(evaluationRecord);
    }

    @GetMapping("/getEvaluationRecordById")
    public JsonResult getEvaluationRecordById(Long id){
        return recordService.getEvaluationRecordById(id);
    }

    //删除指定评价记录方案及其下所有评论内容
    @GetMapping("/deleteEvaluationRecordById")
    public JsonResult deleteEvaluationRecordById(Long id){
        return recordService.deleteEvaluationRecordById(id);
    }

    @GetMapping("/getEvaluationRecord")
    public JsonResult getEvaluationRecord(EvaluationRecord evaluationRecord){
        return recordService.getEvaluationRecord(evaluationRecord);
    }

    @GetMapping("/listClassStudentEvaluationScore")
    public JsonResult listClassStudentEvaluationScore(Student student){
        return recordService.listClassStudentEvaluationScore(student);
    }

    @GetMapping("/getRecordStatisticsForStudent")
    public JsonResult getRecordStatisticsForStudent(String studentUserId){
        return recordService.getRecordStatisticsForStudent(studentUserId);
    }

    @GetMapping("/getRecordStatisticsForStudentInToday")
    public JsonResult getRecordStatisticsForStudentInToday(EvaluationRecord evaluationRecord){
        return recordService.getRecordStatisticsForStudentInToday(evaluationRecord);
    }
}
