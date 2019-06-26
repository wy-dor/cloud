package com.learning.cloud.score.controller;

import com.learning.cloud.score.service.ScoreboardService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreboardController {
    @Autowired
    private ScoreboardService scoreboardService;

    @GetMapping("getSchoolScore")
    public JsonResult getSchoolScore(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return scoreboardService.getSchoolScore(schoolId);
    }

    @GetMapping("updateSchoolScore")
    public JsonResult updateSchoolScore(Long schoolId)throws Exception{
        return scoreboardService.updateSchoolScore(schoolId);
    }

    @GetMapping("getClassScore")
    public JsonResult getClassScore(@RequestParam(value="classId",required = true) Long classId)throws Exception{
        return scoreboardService.getClassScore(classId);
    }

    @GetMapping("updateClassScore")
    public JsonResult updateClassScore(Long classId)throws Exception{
        return scoreboardService.updateClassScore(classId);
    }

    @GetMapping("getSchoolRank")
    public JsonResult getSchoolRank(@RequestParam(value="bureauId",required = true) Long bureauId)throws Exception{
        return scoreboardService.getSchoolRank(bureauId);
    }

    @GetMapping("getTeacherRank")
    public JsonResult getTeacherRank(@RequestParam(value="bureauId",required = true) Long bureauId,
                                    @RequestParam(value="schoolId",required = false) Long schoolId)throws Exception{
        return scoreboardService.getTeacherRank(bureauId, schoolId);
    }

    @GetMapping("getParentRank")
    public JsonResult getParentRank(@RequestParam(value="parentId",required = false) Long parentId)throws Exception{
        return scoreboardService.getParentRank(parentId);
    }
}
