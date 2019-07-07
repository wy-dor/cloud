package com.learning.cloud.course.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CourseExchange {
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Short fromNum;

    private Short toNum;

    private Long fromId;

    private Long toId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDay;

    private String changeInfo;

    private Long createTeacherId;

    private Long acceptTeacherId;

    private Short status;

    private CourseDetail courseDetail;

    private Long classId;

}
