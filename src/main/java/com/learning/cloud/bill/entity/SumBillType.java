package com.learning.cloud.bill.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SumBillType {
    private Integer payType;
    private BigDecimal amount;
}
