package com.learning.cloud.user.parent.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class ParentPhone extends BaseEntity {
    private Integer id;

    private String userId;

    private String phone;
}
