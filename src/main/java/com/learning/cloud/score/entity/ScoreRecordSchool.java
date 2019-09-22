package com.learning.cloud.score.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author: yyt
 * @Date: 2019-09-22 16:00
 * @Desc:
 */
@Data
public class ScoreRecordSchool extends BaseEntity {

    private Long id;

    private Date recordTime;

    private Long scoreTypeId;

    private Long schoolId;

    private Long bureauId;

    private Long scoreBeforeRecord;

    private Long score;

}
