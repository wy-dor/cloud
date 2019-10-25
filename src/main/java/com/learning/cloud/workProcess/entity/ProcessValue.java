package com.learning.cloud.workProcess.entity;

import lombok.Data;

/**
 * @Author: yyt
 * @Date: 2019-10-08 16:42
 * @Desc:
 */
@Data
public class ProcessValue {
    private Integer processId;
    private String createTime;
    private String institution;
    private String title;

    private String deadLine;
    private Attachment attachment;
    private String userId;
    private String depId;
    private String approver;
    private String copyTo;
    private String school;


    @Data
    public class Attachment{
        private String fileId;
        private String fileName;
        private String fileType;
        private String spaceId;
        private String fileSize;
    }
}
