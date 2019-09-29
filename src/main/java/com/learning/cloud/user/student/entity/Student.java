package com.learning.cloud.user.student.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Student extends BaseEntity {
    private Integer id;

    private String studentName;

    private String userId;

    private Integer classId;

    private Integer campusId;

    //后添加
    private Integer schoolId;

    private Integer bureauId;

    private String avatar;

    private Long topClassId;

    private String topStudentNo;
}
