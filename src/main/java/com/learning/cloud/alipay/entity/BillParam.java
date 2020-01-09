package com.learning.cloud.alipay.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-01-10 19:01
 * @Desc:
 */
@Data
public class BillParam {
    private List<UserDetails> users;
    private String school_pid;
    private String school_no;
    private String child_name;
    private String grade;
    private String class_in;
    private String student_code;
    private String student_identify;
    private String out_trade_no;
    private String charge_bill_title;
    private String charge_type;
    private List<ChargeItems> charge_item;
    private BigDecimal amount;
    private String gmt_end;
    private String end_enable;
    private String partner_id;
}
