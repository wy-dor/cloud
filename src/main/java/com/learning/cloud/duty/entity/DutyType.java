package com.learning.cloud.duty.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class DutyType extends BaseEntity {
    private Long id;

    private Long schoolId;

    private String checkName;

    private Integer orgPoint;

}
