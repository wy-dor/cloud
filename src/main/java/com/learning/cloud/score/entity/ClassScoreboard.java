package com.learning.cloud.score.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ClassScoreboard {
    private Long id;

    private Long classId;

    private Long schoolId;

    private Integer score;

    private Date lastTime;

    private Long bureauId;

    private String className;

}
