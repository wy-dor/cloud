package com.learning.cloud.user.admin.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class Administrator extends BaseEntity {
    private Integer id;

    private String userId;

    private String name;

    private Integer schoolId;

    private Short status;

    //added
    private Long sysLevel;
}
