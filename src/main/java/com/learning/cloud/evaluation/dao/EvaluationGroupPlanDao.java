package com.learning.cloud.evaluation.dao;

import com.learning.cloud.evaluation.entity.EvaluationGroupPlan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EvaluationGroupPlanDao {
    EvaluationGroupPlan getById(Long id);

    int deleteById(Long id);

    int insert(EvaluationGroupPlan evaluationGroupPlan);

    List<EvaluationGroupPlan> getByGroupPlan(EvaluationGroupPlan evaluationGroupPlan);

    int update(EvaluationGroupPlan evaluationGroupPlan);

}
