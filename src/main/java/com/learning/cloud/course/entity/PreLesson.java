package com.learning.cloud.course.entity;

import com.learning.domain.BaseEntity;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime == null ? null : uploadTime.trim();
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName == null ? null : termName.trim();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType == null ? null : courseType.trim();
    }

    public String getTeachingTopic() {
        return teachingTopic;
    }

    public void setTeachingTopic(String teachingTopic) {
        this.teachingTopic = teachingTopic == null ? null : teachingTopic.trim();
    }

    public String getPlanHour() {
        return planHour;
    }

    public void setPlanHour(String planHour) {
        this.planHour = planHour == null ? null : planHour.trim();
    }

    public Integer getIsPerformed() {
        return isPerformed;
    }

    public void setIsPerformed(Integer isPerformed) {
        this.isPerformed = isPerformed;
    }

    public String getPerformTime() {
        return performTime;
    }

    public void setPerformTime(String performTime) {
        this.performTime = performTime == null ? null : performTime.trim();
    }

    public Integer getTeachingPlanType() {
        return teachingPlanType;
    }

    public void setTeachingPlanType(Integer teachingPlanType) {
        this.teachingPlanType = teachingPlanType;
    }

    public String getTeachingPicIds() {
        return teachingPicIds;
    }

    public void setTeachingPicIds(String teachingPicIds) {
        this.teachingPicIds = teachingPicIds;
    }

    public String getTeachingGoal() {
        return teachingGoal;
    }

    public void setTeachingGoal(String teachingGoal) {
        this.teachingGoal = teachingGoal == null ? null : teachingGoal.trim();
    }

    public String getTeachingPoint() {
        return teachingPoint;
    }

    public void setTeachingPoint(String teachingPoint) {
        this.teachingPoint = teachingPoint == null ? null : teachingPoint.trim();
    }

    public String getTeachingPreparation() {
        return teachingPreparation;
    }

    public void setTeachingPreparation(String teachingPreparation) {
        this.teachingPreparation = teachingPreparation == null ? null : teachingPreparation.trim();
    }

    public String getTeachingProcess() {
        return teachingProcess;
    }

    public void setTeachingProcess(String teachingProcess) {
        this.teachingProcess = teachingProcess == null ? null : teachingProcess.trim();
    }

    public Integer getIsUploadResources() {
        return isUploadResources;
    }

    public void setIsUploadResources(Integer isUploadResources) {
        this.isUploadResources = isUploadResources;
    }

    public String getCourseAttachUrl() {
        return courseAttachUrl;
    }

    public void setCourseAttachUrl(String courseAttachUrl) {
        this.courseAttachUrl = courseAttachUrl == null ? null : courseAttachUrl.trim();
    }

    public String getOtherAttachUrl() {
        return otherAttachUrl;
    }

    public void setOtherAttachUrl(String otherAttachUrl) {
        this.otherAttachUrl = otherAttachUrl == null ? null : otherAttachUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
