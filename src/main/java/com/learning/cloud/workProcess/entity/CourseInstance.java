package com.learning.cloud.workProcess.entity;

import lombok.Data;

/**
 * @Author: yyt
 * @Date: 2019/11/14 5:18 下午
 * @Desc:
 */
@Data
public class CourseInstance {
    private Integer processId;
    //2班 2019-11-15 第三节 语文课 申请和 2019-11-15 第四节 数学课调换
    private String className;
    private String orgTime;
    private String orgOrder;
    private String orgCourseName;
    private String cTime;
    private String cOrder;
    private String cCourseName;
    private String reason;
    private String userId;
    private String depId;
    private String approver;
    private String copyTo;
}
