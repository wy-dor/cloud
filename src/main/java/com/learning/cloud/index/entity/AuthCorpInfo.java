package com.learning.cloud.index.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthCorpInfo extends BaseEntity {
    private Integer id;

    private String corpId;

    private String corpName;

    private String industry;

    private Integer authLevel;

    private String inviteUrl;

    private Short isAuthenticated;

    private String licenseCode;

    //added
    private Integer industryType;

    private Integer orgId;

    private String suiteKey;

    private String accessToken;
}
