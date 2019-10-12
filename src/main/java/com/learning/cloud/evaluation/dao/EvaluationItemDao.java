package com.learning.cloud.evaluation.dao;

import com.learning.cloud.evaluation.entity.EvaluationItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EvaluationItemDao {
    List<EvaluationItem> getByItem(EvaluationItem evaluationItem);

    EvaluationItem getById(Long id);

    int deleteByItem(EvaluationItem evaluationItem);

    int insert(EvaluationItem evaluationItem);

    int update(EvaluationItem evaluationItem);
}
