package com.learning.cloud.index.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class AuthUserInfo extends BaseEntity {
    private Integer id;

    private String userId;

    private String corpId;
}
