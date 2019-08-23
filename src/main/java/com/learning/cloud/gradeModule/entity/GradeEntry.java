package com.learning.cloud.gradeModule.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class GradeEntry extends BaseEntity {
    private Long id;

    private Long moduleId;

    private Integer studentId;

    private String studentName;

    private String remark;

    private String marks;

    @Getter
    @Setter
    private Integer classId;

    @Getter
    @Setter
    private String jsonStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks == null ? null : marks.trim();
    }
}
