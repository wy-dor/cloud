package com.learning.cloud.evaluation.dao;

import com.learning.cloud.evaluation.entity.EvaluationIcon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EvaluationIconDao {
    EvaluationIcon getById(Long id);

    int deleteById(Long id);

    int insert(EvaluationIcon evaluationIcon);

    List<EvaluationIcon> getByIcon(EvaluationIcon evaluationIcon);

    int update(EvaluationIcon evaluationIcon);
}
