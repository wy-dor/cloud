package com.learning.cloud.ding.onlineSign.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SignRecord extends BaseEntity {
    private Integer id;

    private String parentName;

    private String userId;

    private Integer parentId;

    private Integer signId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date signTime;

    private Long picId;

    private String remark;
}
