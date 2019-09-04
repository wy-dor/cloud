package com.learning.cloud.index.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class CorpAgent extends BaseEntity {
    private Integer id;

    private String agentId;

    private String corpId;

    private String agentName;

    private String appId;

    private String logoUrl;

    //added
    private Date updateTime;
}
