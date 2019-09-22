package com.learning.cloud.score.service;

import com.learning.cloud.score.entity.ScoreRank;
import com.learning.domain.JsonResult;

public interface ScoreboardService {

    JsonResult getSchoolScore(Long schoolId)throws Exception;

    JsonResult updateSchoolScore(Long schoolId)throws Exception;

    JsonResult getClassScore(Long classId)throws Exception;

    JsonResult updateClassScore(Long classId)throws Exception;


    JsonResult getTeacherRank(Long bureauId, Long schoolId)throws Exception;

//    JsonResult getParentRank(Long parentId)throws Exception;

    JsonResult getClassRank(Long schoolId)throws Exception;

    JsonResult getBureauPersonnelRank(ScoreRank scoreRank)throws Exception;

    JsonResult getSchoolRank(ScoreRank scoreRank)throws Exception;

    JsonResult getPersonnelRank(ScoreRank scoreRank)throws Exception;
}
