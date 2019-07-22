package com.learning.cloud.score.controller;

import com.learning.cloud.score.service.ScoreboardService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreboardController {
    @Autowired
    private ScoreboardService scoreboardService;

    @GetMapping("/api/getSchoolScore")
    public JsonResult getSchoolScore(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return scoreboardService.getSchoolScore(schoolId);
    }

    @PostMapping("/api/updateSchoolScore")
    public JsonResult updateSchoolScore(Long schoolId)throws Exception{
        return scoreboardService.updateSchoolScore(schoolId);
    }

    @GetMapping("/api/getClassScore")
    public JsonResult getClassScore(@RequestParam(value="classId",required = true) Long classId)throws Exception{
        return scoreboardService.getClassScore(classId);
    }

    @PostMapping("/api/updateClassScore")
    public JsonResult updateClassScore(Long classId)throws Exception{
        return scoreboardService.updateClassScore(classId);
    }

    @GetMapping("/api/getSchoolRank")
    public JsonResult getSchoolRank(@RequestParam(value="bureauId",required = true) Long bureauId)throws Exception{
        return scoreboardService.getSchoolRank(bureauId);
    }

    @GetMapping("/api/getTeacherRank")
    public JsonResult getTeacherRank(@RequestParam(value="bureauId",required = true) Long bureauId,
                                    @RequestParam(value="schoolId",required = false) Long schoolId)throws Exception{
        return scoreboardService.getTeacherRank(bureauId, schoolId);
    }

    @GetMapping("/api/getClassRank")
    public JsonResult getClassRank(@RequestParam(value="schoolId",required = false) Long schoolId)throws Exception{
        return scoreboardService.getClassRank(schoolId);
    }
}
