package com.learning.cloud.school.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class School extends BaseEntity {
    private Integer id;

    private String schoolName;

    private Integer bureauId;

    private Short state;

    private String corpId;

    //added
    private Long orgActiveUserCount;
    
    private Long orgUserCount;

}
