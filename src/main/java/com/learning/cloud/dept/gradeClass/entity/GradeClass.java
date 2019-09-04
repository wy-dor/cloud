package com.learning.cloud.dept.gradeClass.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class GradeClass extends BaseEntity {
    private Integer id;

    private String className;

    private String gradeName;

    private String sessionName;

    private Integer campusId;

    private Integer headTeacherId;

    private String teacherIds;

    //added
    private String campusName;

    private Integer schoolId;

    private Integer bureauId;

    private Integer teacherNum;

    private Integer parentNum;

    private Integer stuNum;

    private Long deptId;
}
