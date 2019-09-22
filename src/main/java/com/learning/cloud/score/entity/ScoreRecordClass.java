package com.learning.cloud.score.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: yyt
 * @Date: 2019-09-22 17:47
 * @Desc:
 */
@Data
public class ScoreRecordClass {

    private Long id;

    private Date recordTime;

    private Long scoreTypeId;

    private Long schoolId;

    private Long classId;

    private Long scoreBeforeRecord;

    private Long score;
}
