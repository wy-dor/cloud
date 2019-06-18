package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.ScoreRecord;

public interface ScoreRecordMapper {
    int insert(ScoreRecord record);

    int insertSelective(ScoreRecord record);
}