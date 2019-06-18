package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.ScoreType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScoreTypeDao {
    int addScoreType(ScoreType scoreType);

    int editScoreType(ScoreType scoreType);

    ScoreType getScoreTypeById(Long id);

    List<ScoreType> getOrganizeScoreType(ScoreType scoreType);
}
