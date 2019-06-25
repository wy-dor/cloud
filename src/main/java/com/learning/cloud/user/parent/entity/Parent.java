package com.learning.cloud.user.parent.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Parent extends BaseEntity {
    private Integer id;

    private String parentName;

    private String userId;

    private Integer studentId;

    private Integer classId;

    private Integer campusId;

    private Integer schoolId;

    private Integer bureauId;

    private String phone;

}
