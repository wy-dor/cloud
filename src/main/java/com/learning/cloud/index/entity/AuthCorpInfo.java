package com.learning.cloud.index.entity;

import com.learning.domain.BaseEntity;

public class AuthCorpInfo extends BaseEntity {
    private Integer id;

    private String corpId;

    private String corpName;

    private String industry;

    private Integer authLevel;

    private String inviteUrl;

    private Short isAuthenticated;

    private String licenseCode;

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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public Integer getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl == null ? null : inviteUrl.trim();
    }

    public Short getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(Short isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode == null ? null : licenseCode.trim();
    }
}
