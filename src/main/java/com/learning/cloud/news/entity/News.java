package com.learning.cloud.news.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class News extends BaseEntity {
    private Long id;

    private String title;

    private Long picId;

    private String origin;

    private String uploadTime;

    private String updateTime;

    private byte[] detail;

    @Getter
    @Setter
    private Integer status;

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

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime == null ? null : uploadTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public byte[] getDetail() {
        return detail;
    }

    public void setDetail(byte[] detail) {
        this.detail = detail;
    }
}
