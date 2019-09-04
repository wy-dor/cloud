package com.learning.cloud.supervision.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Supervision extends BaseEntity {
    private Long id;

    private String title;

    private Long picId;

    private String origin;

    private Integer clickNum;

    private String uploadTime;

    private Date updateTime;

    private Integer typeId;

    private Integer status;

    private Integer topping;

    private Integer bureauId;

    private String detail;

    //added
    private Integer schoolId;
}
