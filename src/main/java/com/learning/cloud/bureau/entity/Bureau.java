package com.learning.cloud.bureau.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class Bureau extends BaseEntity {
    private Integer id;

    private String bureauName;

    private String proviceName;

    private String cityName;

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

    public String getBureauName() {
        return bureauName;
    }

    public void setBureauName(String bureauName) {
        this.bureauName = bureauName == null ? null : bureauName.trim();
    }

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName == null ? null : proviceName.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }
}
