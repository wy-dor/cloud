package com.learning.cloud.evaluation.entity;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class RecordStatisticsForStudent {

    private String dimensionName;

    private Long iconId;

    private BigDecimal praiseItemCount;

    private BigDecimal criticalItemCount;
}
