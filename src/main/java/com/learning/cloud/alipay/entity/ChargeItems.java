package com.learning.cloud.alipay.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yyt
 * @Date: 2019-01-10 19:08
 * @Desc:
 */
@Data
public class ChargeItems {
    private String item_name;
    private BigDecimal item_price;
    private Integer item_serial_number;
    private Integer item_maximum;
    private String item_mandatory;
}
