package com.learning.cloud.courseWare.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CourseWare {
    private Long id;

    private String cwName;

    private String fileName;

    private String filePath;

    private Date uploadTime;

    private Integer likes;

    private Long teacherId;

    private Long cdId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cdDay;

}
