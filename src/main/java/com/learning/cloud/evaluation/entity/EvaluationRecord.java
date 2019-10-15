package com.learning.cloud.evaluation.entity;

import lombok.Data;

@Data
public class EvaluationRecord {
    private Long id;

    private Long dimensionId;

    private Long itemId;

    private Integer addingWay;

    private String groupIds;

    private String studentUserIds;

    private Long score;

    private String userId;
}
