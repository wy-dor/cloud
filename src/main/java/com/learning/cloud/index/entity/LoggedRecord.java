package com.learning.cloud.index.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LoggedRecord {
    private Integer id;

    private String userId;

    private Date loggedTime;

    private String corpId;

    private Integer time;

}
