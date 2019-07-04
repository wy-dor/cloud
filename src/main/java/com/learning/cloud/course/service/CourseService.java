package com.learning.cloud.course.service;

import com.learning.cloud.course.entity.Course;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-06-11 16:51
 * @Desc:
 */
public interface CourseService {
    JsonResult getCourseByClassId(Long classId)throws Exception;

    JsonResult addCourse(Course course)throws Exception;

    JsonResult editCourse(Course course)throws Exception;

    JsonResult getSchoolCourse(Course course)throws Exception;

    JsonResult publishCourse(Long id)throws Exception;
}
