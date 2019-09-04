package com.learning.cloud.course.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

@Data
public class PreLesson extends BaseEntity {
    private Long id;

    private String uploadTime;

    private String termName;

    private String teacherName;

    private String gradeName;

    private String className;

    private String courseType;

    private String teachingTopic;

    private String planHour;

    private Integer isPerformed;

    private String performTime;

    private Integer teachingPlanType;

    private String teachingPicIds;

    private String teachingGoal;

    private String teachingPoint;

    private String teachingPreparation;

    private String teachingProcess;

    private Integer isUploadResources;

    private String courseAttachUrl;

    private String otherAttachUrl;

    private Integer status;
}
