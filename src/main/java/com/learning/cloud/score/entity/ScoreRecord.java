package com.learning.cloud.score.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ScoreRecord extends BaseEntity {
    private Long id;

    private Date recordTime;

    private Long scoreTypeId;

    private String userId;

    private Long scoreBeforeRecord;

    private Long score;

    private String teacherName;

    private String avatar;

}
