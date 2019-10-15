package com.learning.cloud.evaluation.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class EvaluationIcon extends BaseEntity {
    private Long id;

    private Integer iconType;

    private Integer builtin;

    private Integer sequence;

    private String pic;

    private Integer schoolId;
}
