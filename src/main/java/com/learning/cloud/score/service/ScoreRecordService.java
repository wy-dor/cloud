package com.learning.cloud.score.service;

import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.domain.JsonResult;

public interface ScoreRecordService {
    JsonResult addScoreRecord(ScoreRecord scoreRecord)throws Exception;

    JsonResult getUserScore(String userId)throws Exception;

}
