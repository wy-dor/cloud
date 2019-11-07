package com.learning.cloud.user.teacher.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class Teacher extends BaseEntity {
    private Integer id;

    private String teacherName;

    private String userId;

    private Integer campusId;

    private String classIds;

    private String avatar;

    //后添加
    private Integer schoolId;

    private Integer bureauId;

    private Long courseType;

    private String courseName;

    private Integer dutyInspector;

}
