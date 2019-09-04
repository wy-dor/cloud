package com.learning.cloud.bizData.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SyncBizData {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String subscribeId;

    private String corpId;

    private String bizId;

    private Integer bizType;

    private Long openCursor;

    private Integer status;

    private String bizData;
}
