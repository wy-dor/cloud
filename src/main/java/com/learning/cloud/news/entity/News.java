package com.learning.cloud.news.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class News extends BaseEntity {
    private Long id;

    private String title;

    private Long picId;

    private String origin;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String uploadTime;

    private Date updateTime;

    private byte[] detail;

    private Integer status;
}
