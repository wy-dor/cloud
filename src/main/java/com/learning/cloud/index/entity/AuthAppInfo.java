package com.learning.cloud.index.entity;

import com.learning.domain.BaseEntity;

import java.util.Date;

public class AuthAppInfo extends BaseEntity {
    private Integer id;

    private String corpId;

    private String corpName;

    private String permanentCode;

    private String tempAuthCode;

    private String suiteAccessToken;

    private Date createdTime;

    private String corpAccessToken;

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

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode == null ? null : permanentCode.trim();
    }

    public String getTempAuthCode() {
        return tempAuthCode;
    }

    public void setTempAuthCode(String tempAuthCode) {
        this.tempAuthCode = tempAuthCode == null ? null : tempAuthCode.trim();
    }

    public String getSuiteAccessToken() {
        return suiteAccessToken;
    }

    public void setSuiteAccessToken(String suiteAccessToken) {
        this.suiteAccessToken = suiteAccessToken == null ? null : suiteAccessToken.trim();
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
