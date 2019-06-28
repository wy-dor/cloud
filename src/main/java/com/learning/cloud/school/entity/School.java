package com.learning.cloud.school.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class School extends BaseEntity {
    private Integer id;

    private String schoolName;

    private Integer bureauId;

    private Short state;

    @Getter
    @Setter
    private String corpId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public Integer getBureauId() {
        return bureauId;
    }

    public void setBureauId(Integer bureauId) {
        this.bureauId = bureauId;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }
}
