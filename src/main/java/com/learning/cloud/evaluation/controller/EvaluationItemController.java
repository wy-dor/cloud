package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationItem;
import com.learning.cloud.evaluation.service.EvaluationItemService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationItemController {
    @Autowired
    private EvaluationItemService itemService;

    @GetMapping("/getEvaluationItem")
    public JsonResult getEvaluationItem(EvaluationItem evaluationItem) {
        return itemService.getEvaluationItem(evaluationItem);
    }

    @GetMapping("/getEvaluationItemById")
    public JsonResult getEvaluationItemById(Long id) {
        return itemService.getEvaluationItemById(id);
    }

    @PostMapping("/addEvaluationItem")
    public JsonResult addEvaluationItem(EvaluationItem evaluationItem) throws Exception {
        return itemService.addEvaluationItem(evaluationItem);
    }

    @PostMapping("/updateEvaluationItem")
    public JsonResult updateEvaluationItem(EvaluationItem evaluationItem) throws Exception {
        return itemService.updateEvaluationItem(evaluationItem);
    }

    @GetMapping("/deleteEvaluationItemById")
    public JsonResult deleteEvaluationItemById(Long id) {
        return itemService.deleteEvaluationItemById(id);
    }

    @PostMapping("/batchUpdateEvaluationItem")
    public JsonResult batchUpdateEvaluationItem(String ids, Integer status) {
        return itemService.batchUpdateEvaluationItem(ids, status);
    }
}

