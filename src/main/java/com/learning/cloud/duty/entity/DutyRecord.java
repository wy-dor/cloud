package com.learning.cloud.duty.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DutyRecord {
    private Long id;

    private Long schoolId;

    private Long campusId;

    private Long classId;

    private Integer pointType;

    private Short time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String day;

    private String userId;

    private Short status;

    private String name;

    private BigDecimal point;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ymdDay;

    private String userName;

    private String picIds;

}
