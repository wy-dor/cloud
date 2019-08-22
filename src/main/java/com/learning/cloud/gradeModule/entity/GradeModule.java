package com.learning.cloud.gradeModule.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    private Integer classesAddingWay;

    @Getter
    @Setter
    private String gradeNamesStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getScoringRoles() {
        return scoringRoles;
    }

    public void setScoringRoles(Integer scoringRoles) {
        this.scoringRoles = scoringRoles;
    }

    public Integer getRankModule() {
        return rankModule;
    }

    public void setRankModule(Integer rankModule) {
        this.rankModule = rankModule;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects == null ? null : subjects.trim();
    }

    public String getClassesStr() {
        return classesStr;
    }

    public void setClassesStr(String classesStr) {
        this.classesStr = classesStr == null ? null : classesStr.trim();
    }

    public Integer getVisibleRange() {
        return visibleRange;
    }

    public void setVisibleRange(Integer visibleRange) {
        this.visibleRange = visibleRange;
    }

    public Integer getNotifyToEntry() {
        return notifyToEntry;
    }

    public void setNotifyToEntry(Integer notifyToEntry) {
        this.notifyToEntry = notifyToEntry;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline == null ? null : deadline.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
