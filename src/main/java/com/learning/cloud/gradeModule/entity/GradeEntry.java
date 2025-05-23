package com.learning.cloud.gradeModule.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class GradeEntry extends BaseEntity {
    private Long id;

    private Long moduleId;

    private String studentNo;

    private String studentName;

    private String remark;

    private String marks;

    //added
    private Integer classId;

    private String term;

    private BigDecimal totalPoints;
}
