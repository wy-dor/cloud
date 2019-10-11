package com.learning.cloud.bureau.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Bureau extends BaseEntity {
    private Integer id;

    private String bureauName;

    private String provinceName;

    private String cityName;

    private Short state;

    //后添加
    private String corpId;

    private Long orgActiveUserCount;

    private Long orgUserCount;
}
