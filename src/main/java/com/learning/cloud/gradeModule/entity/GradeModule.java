package com.learning.cloud.gradeModule.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class GradeModule extends BaseEntity {
    private Long id;

    private String title;

    private String term;

    private String startTime;

    private String endTime;

    private String type;

    private Integer scoringRoles;

    private Integer rankModule;

    private String subjects;

    private String classesStr;

    private Integer visibleRange;

    private Integer notifyToEntry;

    private String deadline;

    private Integer status;

    //added
    private Integer classesAddingWay;

    private List<String> gradeNamesStr;

    private Integer schoolId;

    private String userId;

    private String publishTime;

    private String createTime;

}
