package com.learning.cloud.evaluation.dao;

import com.learning.cloud.evaluation.entity.EvaluationRemark;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EvaluationRemarkDao {
    List<EvaluationRemark> getByRemark(EvaluationRemark evaluationRemark);

    EvaluationRemark getById(Long id);

    int deleteByRemark(EvaluationRemark evaluationRemark);

    int insert(EvaluationRemark evaluationRemark);

    int update(EvaluationRemark evaluationRemark);
}
