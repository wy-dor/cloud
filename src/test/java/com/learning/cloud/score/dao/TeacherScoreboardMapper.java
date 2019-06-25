package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.TeacherScoreboard;

public interface TeacherScoreboardMapper {
    int insert(TeacherScoreboard record);

    int insertSelective(TeacherScoreboard record);
}