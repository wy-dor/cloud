package com.learning.cloud.evaluation.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EvaluationStudentScore extends BaseEntity {
    private String studentName;

    private String userId;

    private String studentNo;

    private String avatar;

    private BigDecimal totalScore;
}
