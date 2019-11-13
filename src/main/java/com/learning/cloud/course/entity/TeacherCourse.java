package com.learning.cloud.course.entity;

import lombok.Data;

/**
 * @Author: yyt
 * @Date: 2019/11/11 10:11 上午
 * @Desc:
 */
@Data
public class TeacherCourse {

    private Integer teacherId;
    private Integer classId;
    private Integer courseTypeId;
    private Integer schoolId;
}
