package com.learning.cloud.workProcess.entity;

import lombok.Data;

@Data
public class ProcessInstance {
    private Integer id;

    private Integer processId;

    private String agentId;

    private String attachment;

    private String formComponentValues;

    private String processInstanceId;

    private Integer parentId;

    private Short status;

}
