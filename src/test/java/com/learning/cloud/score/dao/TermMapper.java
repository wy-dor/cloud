package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.Term;

public interface TermMapper {
    int insert(Term record);

    int insertSelective(Term record);
}