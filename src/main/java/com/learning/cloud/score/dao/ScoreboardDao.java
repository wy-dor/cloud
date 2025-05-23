package com.learning.cloud.score.dao;

import com.learning.cloud.score.entity.ClassScoreboard;
import com.learning.cloud.score.entity.SchoolScoreboard;
import com.learning.cloud.score.entity.ScoreRank;
import com.learning.cloud.score.entity.ScoreRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScoreboardDao {

    SchoolScoreboard getSchoolScore(Long schoolId);

    int addSchoolScoreboard(SchoolScoreboard schoolScoreboard);

    ClassScoreboard getClassScore(Long classId);

    int addClassScoreboard(ClassScoreboard classScoreboard);

    List<ScoreRank> getTeacherRank(ScoreRank scoreRank);

    List<ClassScoreboard> getClassRank(Long schoolId);

    List<ScoreRank> getBureauPersonnelRank(ScoreRank scoreRank);

    List<ScoreRank> getSchoolRank(ScoreRank scoreRank);

    List<ScoreRank> getPersonnelRank(ScoreRank scoreRank);

    List<ScoreRank> getClassRankFromDuty(ScoreRank scoreRank);
}
