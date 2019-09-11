package com.learning.cloud.supervision.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Inspector extends BaseEntity {
    private Long id;

    private String login;

    private String password;

    private String name;

    private String phone;

    private Date createTime;

    private Integer bureauId;
}
