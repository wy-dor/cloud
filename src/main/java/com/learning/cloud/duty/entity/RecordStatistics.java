package com.learning.cloud.duty.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecordStatistics {
    private Integer classId;

    private String className;

    private String day;

//    private String weekday;

    private BigDecimal point;
}
