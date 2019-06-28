package com.learning.cloud.dept.campus.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class Campus extends BaseEntity {
    private Integer id;

    private String campusName;

    private Integer schoolId;

    private Integer campusType;

    private Integer state;

    @Getter
    @Setter
    private Long deptId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName == null ? null : campusName.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getCampusType() {
        return campusType;
    }

    public void setCampusType(Integer campusType) {
        this.campusType = campusType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
