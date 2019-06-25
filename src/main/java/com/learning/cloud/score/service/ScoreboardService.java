package com.learning.cloud.score.service;

import com.learning.domain.JsonResult;

public interface ScoreboardService {

    JsonResult getSchoolScore(Long schoolId)throws Exception;

    JsonResult updateSchoolScore(Long schoolId)throws Exception;

    JsonResult getClassScore(Long classId)throws Exception;

    JsonResult updateClassScore(Long classId)throws Exception;

    JsonResult getSchoolRank(Long bureauId)throws Exception;

    JsonResult getTeacherRank(Long bureauId, Long schoolId)throws Exception;
}
