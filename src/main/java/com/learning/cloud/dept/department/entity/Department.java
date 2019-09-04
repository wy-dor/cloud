package com.learning.cloud.dept.department.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class Department extends BaseEntity {
    private Long id;

    private String deptId;

    private String name;

    private String parentId;

    private Short outerDept;

    private String deptManagerUseridList;

    private Short groupContainSubDept;

    //added
    private Short deptHiding;

    private String corpId;
}
