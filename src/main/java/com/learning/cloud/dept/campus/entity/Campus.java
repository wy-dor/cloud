package com.learning.cloud.dept.campus.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Campus extends BaseEntity {
    private Integer id;

    private String campusName;

    private Integer schoolId;

    private Integer campusType;

    private Integer state;

    //后添加
    private Long deptId;

    private Long topCampusId;
}
