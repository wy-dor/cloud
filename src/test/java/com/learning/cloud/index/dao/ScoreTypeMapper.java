package com.learning.cloud.index.dao;

import com.learning.cloud.index.entity.ScoreType;

public interface ScoreTypeMapper {
    int insert(ScoreType record);

    int insertSelective(ScoreType record);
}