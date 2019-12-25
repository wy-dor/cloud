package com.learning.cloud.score.service;

import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.cloud.score.entity.ScoreRecordClass;
import com.learning.cloud.score.entity.ScoreRecordSchool;
import com.learning.domain.JsonResult;

public interface ScoreRecordService {
    JsonResult addScoreRecord(ScoreRecord scoreRecord) throws Exception;

    JsonResult getUserScore(String userId) throws Exception;

    JsonResult addScoreRecordSchool(ScoreRecordSchool scoreRecordSchool) throws Exception;

    JsonResult addScoreRecordClass(ScoreRecordClass scoreRecordClass) throws Exception;
}
