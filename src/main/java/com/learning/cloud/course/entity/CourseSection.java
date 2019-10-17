package com.learning.cloud.course.entity;

import lombok.Data;

@Data
public class CourseSection {
    private Long id;

    private Long sectionId;

    private Long schoolId;

    private String timeFrame;

    private String name;

    private String start;

    private String end;

    private Short type;

}
