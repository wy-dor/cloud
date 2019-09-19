package com.learning.cloud.sendMsg.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-17 14:38
 * @Desc:
 */
@Data
public class WorkMsg {
    private String corpId;
    private String agentId;
    private List<String> userIdList;
    private List<String> deptIdList;
    private Boolean toAllUser;
}
