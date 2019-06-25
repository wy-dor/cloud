package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.SchoolScoreboard;

public interface SchoolScoreboardMapper {
    int insert(SchoolScoreboard record);

    int insertSelective(SchoolScoreboard record);
}