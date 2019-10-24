package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationGroup;
import com.learning.cloud.evaluation.service.EvaluationGroupService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EvaluationGroupController {
    @Autowired
    private EvaluationGroupService groupService;

    @GetMapping("/getEvaluationGroup")
    public JsonResult getEvaluationGroup(EvaluationGroup evaluationGroup){
        List<EvaluationGroup> groupList = groupService.getEvaluationGroup(evaluationGroup);
        return JsonResultUtil.success(new PageEntity<>(groupList));
    }

    @GetMapping("/getEvaluationGroupById")
    public JsonResult getEvaluationGroupById(Long id){
        return groupService.getEvaluationGroupById(id);
    }

    @PostMapping("/addEvaluationGroup")
    public JsonResult addEvaluationGroup(EvaluationGroup evaluationGroup) throws Exception {
        return groupService.addEvaluationGroup(evaluationGroup);
    }

    @PostMapping("/updateEvaluationGroup")
    public JsonResult updateEvaluationGroup(EvaluationGroup evaluationGroup)  throws Exception{
        return groupService.updateEvaluationGroup(evaluationGroup);
    }

    @GetMapping("/deleteEvaluationGroupById")
    public JsonResult deleteEvaluationGroupById(Long id){
        return groupService.deleteEvaluationGroupById(id);
    }

    @PostMapping("/batchUpdateEvaluationGroup")
    public JsonResult batchUpdateEvaluationGroup(String ids,Integer status){
        return groupService.batchUpdateEvaluationGroup(ids,status);
    }
}

