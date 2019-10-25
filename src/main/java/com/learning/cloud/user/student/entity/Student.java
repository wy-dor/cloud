package com.learning.cloud.user.student.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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

    private String studentNo;

    private BigDecimal totalScore;

    //用于标识选择学生评价分数的排序方式
    //1按姓名排序，2按学号排序，3按总分排序
    private Integer scoreSort;
}
