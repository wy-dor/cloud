package com.learning.cloud.index.entity;

import lombok.Data;

/**
 * @Author: yyt
 * @Date: 2019-09-03 17:40
 * @Desc:
 */
@Data
public class SysUserInfo {
    private String userid;
    private String cropid;
    private String cropName;
    private boolean isAdmin;
    private Integer role;
    private Integer schoolId;
    private Long id;
    private Integer bureauId;
    private String classId;
    private Integer teacherId;

}
