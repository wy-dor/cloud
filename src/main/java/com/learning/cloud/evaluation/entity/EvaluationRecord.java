package com.learning.cloud.evaluation.entity;

import com.learning.cloud.user.student.entity.Student;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EvaluationRecord {
    private Long id;

    private Long dimensionId;

    private Long itemId;

    private Integer addingWay;

    private String groupIds;

    private String studentUserIds;

    private BigDecimal score;

    private String userId;

    private List<Student> studentList;

    private String recordName;
}
