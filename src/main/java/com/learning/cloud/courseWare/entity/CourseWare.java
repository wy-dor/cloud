package com.learning.cloud.courseWare.entity;

import lombok.Data;

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

    private Date cdDay;

}
