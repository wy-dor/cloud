package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.ScoreType;

public interface ScoreTypeMapper {
    int insert(ScoreType record);

    int insertSelective(ScoreType record);
}