package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.SchoolScoreboard;
import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.cloud.score.entity.ScoreRecordClass;
import com.learning.cloud.score.entity.ScoreRecordSchool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScoreRecordDao {
    int addScoreRecord(ScoreRecord scoreRecord);

    ScoreRecord getLastScoreRecord(String userId);

    List<ScoreRecord> getRecordTimeByType(@Param("scoreTypeId") Long scoreTypeId, @Param("userId") String userId);

    ScoreRecordSchool getLastScoreRecordSchool(Long schoolId);

    List<ScoreRecord> getRecordSchoolTimeByType(@Param("scoreTypeId") Long scoreTypeId, @Param("schoolId") Long schoolId);

    int addScoreRecordSchool(ScoreRecordSchool scoreRecordSchool);

    ScoreRecordClass getLastScoreRecordClass(Long classId);

    List<ScoreRecord> getRecordClassTimeByType(Long scoreTypeId, Long classId);

    int addScoreRecordClass(ScoreRecordClass scoreRecordClass);
}
