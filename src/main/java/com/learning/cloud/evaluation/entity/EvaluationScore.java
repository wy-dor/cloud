package com.learning.cloud.evaluation.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EvaluationScore {
    private Long id;

    private String userId;

    private String userName;

    private String avatar;

    private String studentNo;

    private BigDecimal totalScore;
}
