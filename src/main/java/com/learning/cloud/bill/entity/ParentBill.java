package com.learning.cloud.bill.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ParentBill extends BaseEntity {
    private Integer id;

    private String userId;

    private Integer schoolId;

    private Integer campusId;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date sendTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date endTime;

    private Short state;

    private Integer classId;

    private String amountStr;

    private String remark;

    private String chargeItems;
}
