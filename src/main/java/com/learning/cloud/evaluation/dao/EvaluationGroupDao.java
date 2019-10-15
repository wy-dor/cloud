package com.learning.cloud.evaluation.dao;

import com.learning.cloud.evaluation.entity.EvaluationGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EvaluationGroupDao {
    EvaluationGroup getById(Long id);

    int insert(EvaluationGroup evaluationGroup);

    List<EvaluationGroup> getByGroup(EvaluationGroup evaluationGroup);

    int update(EvaluationGroup evaluationGroup);

    int deleteByGroup(EvaluationGroup evaluationGroup);
}
