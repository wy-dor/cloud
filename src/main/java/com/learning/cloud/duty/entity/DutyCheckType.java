package com.learning.cloud.duty.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yyt
 * @Date: 2019-09-16 10:31
 * @Desc:
 */
@Data
public class DutyCheckType {

    private Long id;
    private Long dutyTypeId;
    private String name;
    private BigDecimal point;
    private Short plusMinus;
}
