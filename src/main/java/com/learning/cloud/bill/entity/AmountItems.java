package com.learning.cloud.bill.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AmountItems extends BaseEntity {
    private BigDecimal totalAmount;

    private Integer billCount;

    private Integer agentId;

    private String agentName;

    private Integer schoolId;

    private String schoolName;

    private Integer role;


}
