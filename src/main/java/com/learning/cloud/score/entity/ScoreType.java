package com.learning.cloud.score.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class ScoreType extends BaseEntity {
    private Long id;

    private Long organizeId;

    private String actionName;

    private Integer score;

    private Integer time;

}
