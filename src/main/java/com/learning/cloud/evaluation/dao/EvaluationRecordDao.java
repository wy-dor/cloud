package com.learning.cloud.evaluation.dao;

import com.learning.cloud.evaluation.entity.EvaluationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EvaluationRecordDao {
    EvaluationRecord getById(Long id);

    int deleteById(Long id);

    int insert(EvaluationRecord evaluationRecord);

    List<EvaluationRecord> getByRecord(EvaluationRecord evaluationRecord);

    int update(EvaluationRecord evaluationRecord);
}
