package com.learning.cloud.course.entity;

import lombok.Data;

@Data
public class CourseDetail {
    private Long id;

    private Long classId;

    private Long courseId;

    private Integer weekDay;

    private Integer courseNum;

    private String courseTime;

    private Long courseType;

    private String courseName;

    private Long courseTeacherId;

    private String courseTeacherName;

    private Short weekType;

    private Long courseTypeS;

    private String courseNameS;

    private Long courseTeacherIdS;

    private String courseTeacherNameS;

}