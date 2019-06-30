package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.CourseSectionArray;

public interface CourseSectionArrayMapper {
    int insert(CourseSectionArray record);

    int insertSelective(CourseSectionArray record);
}