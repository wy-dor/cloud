package com.learning.cloud.bill.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BillSearch {

    private Integer schoolId;

    //账单付款时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date end;

    private Integer campusId;
}
