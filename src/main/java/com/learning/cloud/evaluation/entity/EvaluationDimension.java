package com.learning.cloud.evaluation.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class EvaluationDimension extends BaseEntity {
    private Long id;

    private String dimensionName;

    private Long iconId;

    private Integer schoolId;

    private String updateUserId;

    private String updateUserName;

    private Integer builtin;
}
