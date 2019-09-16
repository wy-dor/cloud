package com.learning.cloud.duty.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DutyRecord {
    private Long id;

    private Long schoolId;

    private Long campusId;

    private Long classId;

    private Integer pointType;

    private Short time;

    private Date day;

    private Long userId;

    private Short status;

}
