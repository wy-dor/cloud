package com.learning.cloud.user.user.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class User extends BaseEntity {
    private Long id;

    private String userId;

    private String userName;

    private Integer schoolId;

    private Integer campusId;

    @Getter
    @Setter
    private String unionId;

    @Getter
    @Setter
    private Integer roleType;

    @Getter
    @Setter
    private String avatar;

    @Getter
    @Setter
    private Short active;

    @Getter
    @Setter
    private Integer supervisor;

    @Getter
    @Setter
    private String corpId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }
}
