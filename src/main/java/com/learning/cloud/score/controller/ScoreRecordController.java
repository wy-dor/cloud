package com.learning.cloud.score.controller;

import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.cloud.score.entity.ScoreRecordSchool;
import com.learning.cloud.score.service.ScoreRecordService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreRecordController {
    @Autowired
    private ScoreRecordService scoreRecordService;

    @PostMapping("/addScoreRecord")
    public JsonResult addScoreRecord(ScoreRecord scoreRecord)throws Exception{
        return scoreRecordService.addScoreRecord(scoreRecord);
    }

    @GetMapping("/getUserScore")
    public JsonResult getUserScore(@RequestParam(value="userId",required = true) String userId)throws Exception{
        return scoreRecordService.getUserScore(userId);
    }

    @PostMapping("/addScoreRecordSchool")
    public JsonResult addScoreRecordSchool(ScoreRecordSchool scoreRecordSchool)throws Exception{
        return scoreRecordService.addScoreRecordSchool(scoreRecordSchool);
    }



}
