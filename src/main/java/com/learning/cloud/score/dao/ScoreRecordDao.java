package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.SchoolScoreboard;
import com.learning.cloud.score.entity.ScoreRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScoreRecordDao {
    int addScoreRecord(ScoreRecord scoreRecord);

    ScoreRecord getLastScoreRecord(String userId);

    List<ScoreRecord> getRecordTimeByType(Long scoreTypeId);

}
