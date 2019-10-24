package com.learning.cloud.workProcess.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Process {
    private Integer id;

    private Integer bureauId;

    private String corpId;

    private String agentId;

    private String processCode;

    private Short status;

    private Date createTime;

    private String disableFormEdit;

    private String name;

    private String description;

    private String formComponentList;

}
