package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.ClassScoreboard;

public interface ClassScoreboardMapper {
    int insert(ClassScoreboard record);

    int insertSelective(ClassScoreboard record);
}