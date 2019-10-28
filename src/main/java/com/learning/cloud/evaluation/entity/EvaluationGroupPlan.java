package com.learning.cloud.evaluation.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class EvaluationGroupPlan extends BaseEntity {
    private Long id;

    private String name;

    private String userId;

    private String userName;

    private Integer classId;

    //added
    private List<EvaluationGroup> groupList;

    private List<StuInfo> stuInfoList;

    private Integer scoreSort;
}
