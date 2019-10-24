package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationGroup;
import com.learning.cloud.evaluation.service.EvaluationGroupService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class EvaluationGroupController {
    @Autowired
    private EvaluationGroupService evaluationGroupService;

    @GetMapping("/getEvaluationGroup")
    public JsonResult getEvaluationGroup(EvaluationGroup evaluationGroup){
        List<EvaluationGroup> groupList = evaluationGroupService.getEvaluationGroup(evaluationGroup);
        return JsonResultUtil.success(new PageEntity<>(groupList));
    }

    @GetMapping("/getEvaluationGroupById")
    public JsonResult getEvaluationGroupById(Long id){
        return evaluationGroupService.getEvaluationGroupById(id);
    }

    @PostMapping("/addEvaluationGroup")
    public JsonResult addEvaluationGroup(EvaluationGroup evaluationGroup) throws Exception {
        return evaluationGroupService.addEvaluationGroup(evaluationGroup);
    }

    @PostMapping("/updateEvaluationGroup")
    public JsonResult updateEvaluationGroup(EvaluationGroup evaluationGroup)  throws Exception{
        return evaluationGroupService.updateEvaluationGroup(evaluationGroup);
    }

    @GetMapping("/deleteEvaluationGroupById")
    public JsonResult deleteEvaluationGroupById(Long id){
        return evaluationGroupService.deleteEvaluationGroupById(id);
    }

    @PostMapping("/batchUpdateEvaluationGroup")
    public JsonResult batchUpdateEvaluationGroup(String ids,Integer status){
        return evaluationGroupService.batchUpdateEvaluationGroup(ids,status);
    }
}

