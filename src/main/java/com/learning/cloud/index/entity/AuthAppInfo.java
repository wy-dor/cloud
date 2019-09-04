package com.learning.cloud.index.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class AuthAppInfo extends BaseEntity {
    private Integer id;

    private String corpId;

    private String corpName;

    private Date createdTime;

    private String corpAccessToken;

    //added
    private Date updateTime;

    private Integer state;

    private String suiteTicket;
}
