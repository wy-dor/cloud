package com.learning.cloud.dept.topApi.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class EduPeriod extends BaseEntity {
    private Integer id;

    private Integer schoolId;

    private Long topPeriodId;

    private String periodName;

    private String periodType;

    private Long topCampusId;
}
