package com.learning.cloud.evaluation.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learning.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EvaluationGroup extends BaseEntity {
    private Long id;

    private Long groupPlanId;

    private Long iconId;

    private String userId;

    private String studentUserIds;

    private List<StuInfo> stuList;

    private String groupName;

    private String createTime;

    private BigDecimal totalScore;
}
