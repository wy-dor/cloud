package com.learning.cloud.news.entity;

import lombok.Data;

@Data
public class NewsType {
    private Integer id;

    private String typeName;

    private Integer parentId;
}
