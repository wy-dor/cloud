package com.learning.cloud.evaluation.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class EvaluationGroup extends BaseEntity {
    private Long id;

    private Long groupPlanId;

    private Long iconId;

    private Integer userId;

    private String studentUserIds;

    private List<StuInfo> stuList;
}
