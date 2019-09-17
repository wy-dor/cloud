package com.learning.cloud.user.user.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class User extends BaseEntity {
    private Long id;

    private String userId;

    private String userName;

    private Integer schoolId;

    private Integer campusId;

    //后添加
    private String unionId;

    private Integer roleType;

    private String avatar;

    private Short active;

    private Integer supervisor;

    private String corpId;

}
