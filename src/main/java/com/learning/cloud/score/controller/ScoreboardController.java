package com.learning.cloud.score.controller;

import com.learning.cloud.score.entity.ScoreRank;
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

    @GetMapping("/getSchoolScore")
    public JsonResult getSchoolScore(@RequestParam(value = "schoolId", required = true) Long schoolId) throws Exception {
        return scoreboardService.getSchoolScore(schoolId);
    }

    @PostMapping("/updateSchoolScore")
    public JsonResult updateSchoolScore(Long schoolId) throws Exception {
        return scoreboardService.updateSchoolScore(schoolId);
    }

    @GetMapping("/getClassScore")
    public JsonResult getClassScore(@RequestParam(value = "classId", required = true) Long classId) throws Exception {
        return scoreboardService.getClassScore(classId);
    }

    @PostMapping("/updateClassScore")
    public JsonResult updateClassScore(Long classId) throws Exception {
        return scoreboardService.updateClassScore(classId);
    }


    @GetMapping("/getTeacherRank")
    public JsonResult getTeacherRank(ScoreRank scoreRank) throws Exception {
        return scoreboardService.getTeacherRank(scoreRank);
    }

//    @GetMapping("/getClassRank")
//    public JsonResult getClassRank(@RequestParam(value="schoolId",required = false) Long schoolId)throws Exception{
//        return scoreboardService.getClassRank(schoolId);
//    }

    // 获取教育局内部人员排名
    @GetMapping("/getBureauPersonnelRank")
    public JsonResult getBureauPersonnelRank(ScoreRank scoreRank) throws Exception {
        return scoreboardService.getBureauPersonnelRank(scoreRank);
    }

    // 获取学校排名
    @GetMapping("/getSchoolRank")
    public JsonResult getSchoolRank(ScoreRank scoreRank) throws Exception {
        return scoreboardService.getSchoolRank(scoreRank);
    }

    @GetMapping("/getPersonnelRank")
    public JsonResult getPersonnelRank(ScoreRank scoreRank) throws Exception {
        return scoreboardService.getPersonnelRank(scoreRank);
    }

    //获取班级积分排名，每日积分
    @GetMapping("/getClassRankFromDuty")
    public JsonResult getClassRankFromDuty(ScoreRank scoreRank) throws Exception {
        return scoreboardService.getClassRankFromDuty(scoreRank);
    }
}
