package com.learning.cloud.course.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Course extends BaseEntity {
    private Long id;

    private Long schoolId;

    private Long classId;

    private String gradeName;

    private Long termId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date lastTime;

    private Long lastModifier;

    private Short status;

    private String content;

    private String termName;

    private String className;

}
