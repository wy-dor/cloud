package com.learning.cloud.score.controller;

import com.learning.cloud.score.entity.ScoreType;
import com.learning.cloud.score.service.ScoreTypeService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 积分类型
 */
@RestController
public class ScoreTypeController {

    @Autowired
    private ScoreTypeService scoreTypeService;

    @PostMapping("/addScoreType")
    public JsonResult addScoreType(ScoreType scoreType) throws Exception{
       return  scoreTypeService.addScoreType(scoreType);
    }

    @PostMapping("/editScoreType")
    public JsonResult editScoreType(ScoreType scoreType) throws Exception {
        return scoreTypeService.editScoreType(scoreType);
    }

    @GetMapping("/getOrganizeScoreType")
    public JsonResult getOrganizeScoreType(ScoreType scoreType)throws Exception{
        return scoreTypeService.getOrganizeScoreType(scoreType);
    }
}
