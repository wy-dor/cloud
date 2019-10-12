package com.learning.cloud.evaluation.dao;

import com.learning.cloud.evaluation.entity.EvaluationDimension;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EvaluationDimensionDao {

    List<EvaluationDimension> getByDimension(EvaluationDimension evaluationDimension);

    int deleteById(Long id);

    int insert(EvaluationDimension evaluationDimension);

    int update(EvaluationDimension evaluationDimension);

    EvaluationDimension getById(Long id);
}
