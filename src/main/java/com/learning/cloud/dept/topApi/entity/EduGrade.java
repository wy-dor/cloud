package com.learning.cloud.dept.topApi.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class EduGrade extends BaseEntity {
    private Integer id;

    private Integer schoolId;

    private String gradeName;

    private Long topCampusId;

    private Long topPeriodId;

    private Long topGradeId;

    private Long gradeLevel;

    private String startYear;
}
