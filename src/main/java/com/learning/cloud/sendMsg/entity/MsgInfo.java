package com.learning.cloud.sendMsg.entity;

import lombok.Data;

/**
 * @Author: yyt
 * @Date: 2019-09-17 17:15
 * @Desc:
 */
@Data
public class MsgInfo {
    private String corpId;
    private String title;
    private String text;
    private String messageUrl;
    private String picUrl;
}
