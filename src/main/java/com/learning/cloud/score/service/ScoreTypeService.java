package com.learning.cloud.score.service;

import com.learning.cloud.score.entity.ScoreType;
import com.learning.domain.JsonResult;

public interface ScoreTypeService {
    JsonResult addScoreType(ScoreType scoreType) throws Exception;

    JsonResult editScoreType(ScoreType scoreType) throws Exception;

    JsonResult getOrganizeScoreType(ScoreType scoreType) throws Exception;
}
