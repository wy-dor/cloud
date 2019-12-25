package com.learning.cloud.course.service;

import com.learning.cloud.course.entity.CourseType;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-06-10 16:46
 * @Desc:
 */
public interface CourseTypeService {
    JsonResult getSchoolCourseType(Long schoolId) throws Exception;

    JsonResult addSchoolCourseType(CourseType courseType) throws Exception;

    JsonResult deleteCourseType(Long id) throws Exception;
}
