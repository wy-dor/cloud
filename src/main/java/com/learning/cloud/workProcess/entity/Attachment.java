package com.learning.cloud.workProcess.entity;

import lombok.Data;

/**
 * @Author: yyt
 * @Date: 2019/10/25 9:42 上午
 * @Desc:
 */
@Data
public class Attachment{
    private String fileId;
    private String fileName;
    private String fileType;
    private String spaceId;
    private String fileSize;
}
