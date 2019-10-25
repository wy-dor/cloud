package com.learning.cloud.evaluation.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EvaluationItem extends BaseEntity {
    private Long id;

    private String itemName;

    private Long dimensionId;

    private Integer fixed;

    private BigDecimal fixedValue;

    private BigDecimal maxValue;

    private Long iconId;

    private Integer plusMinus;

    private Integer favorite;

    private Integer builtin;

    private Integer schoolId;
}
