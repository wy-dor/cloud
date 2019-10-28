package com.learning.cloud.evaluation.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class EvaluationRemark extends BaseEntity {
    private Long id;

    private Long recordId;

    private String content;

    private String userId;

    private Integer roleType;

    private String userName;

    private String updateTime;
}
