package com.learning.cloud.dept.gradeClass.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class GradeClass extends BaseEntity {
    private Integer id;

    private String className;

    private String gradeName;

    private String sessionName;

    private Integer campusId;

    private Integer headTeacherId;

    private String teacherIds;

    @Getter
    @Setter
    private Integer schoolId;

    @Getter
    @Setter
    private Integer bureauId;

    @Getter
    @Setter
    private Integer teacherNum;

    @Getter
    @Setter
    private Integer parentNum;

    @Getter
    @Setter
    private Integer stuNum;

    @Getter
    @Setter
    private Long deptId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName == null ? null : sessionName.trim();
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public Integer getHeadTeacherId() {
        return headTeacherId;
    }

    public void setHeadTeacherId(Integer headTeacherId) {
        this.headTeacherId = headTeacherId;
    }

    public String getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(String teacherIds) {
        this.teacherIds = teacherIds == null ? null : teacherIds.trim();
    }
}
