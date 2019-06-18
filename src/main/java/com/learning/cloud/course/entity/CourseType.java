package com.learning.cloud.course.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: yyt
 * @Date: 2019-06-10 18:20
 * @Desc:
 */
@Data
public class CourseType extends BaseEntity {
    private Long id;
    private Long schoolId;
    private String courseName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private String editTime;
    private Integer status;
}
