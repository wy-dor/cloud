package com.learning.cloud.index.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class AuthAppInfo extends BaseEntity {
    private Integer id;

    private String corpId;

    private String corpName;

    private Date createdTime;

    private String corpAccessToken;

    @Getter
    @Setter
    private Date updateTime;

    @Getter
    @Setter
    private Integer state;

    @Getter
    @Setter
    private String suiteTicket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId == null ? null : corpId.trim();
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCorpAccessToken() {
        return corpAccessToken;
    }
    
    public void setCorpAccessToken(String corpAccessToken) {
        this.corpAccessToken = corpAccessToken == null ? null : corpAccessToken.trim();
    }
}
