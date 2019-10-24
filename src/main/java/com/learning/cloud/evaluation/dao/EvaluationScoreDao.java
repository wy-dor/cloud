package com.learning.cloud.evaluation.dao;

import com.learning.cloud.evaluation.entity.EvaluationScore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EvaluationScoreDao {
    EvaluationScore getById(Long id);
    
    EvaluationScore getByUserId(String userId);

    int deleteById(Long id);

    int insert(EvaluationScore evaluationScore);

    List<EvaluationScore> getByScore(EvaluationScore evaluationScore);

    int update(EvaluationScore evaluationScore);
}
