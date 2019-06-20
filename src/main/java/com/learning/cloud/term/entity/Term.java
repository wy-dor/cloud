package com.learning.cloud.term.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Term {
    private Long id;

    private Long schoolId;

    private String termName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    private String termYear;

}
