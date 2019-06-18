package com.learning.cloud.course.service;

import com.learning.cloud.course.entity.CourseDetail;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-06-12 19:22
 * @Desc:
 */
public interface CourseDetailService {
    JsonResult getCourseDetailByClassId(Long classId, Integer weekDay)throws Exception;

    JsonResult addCourseDetail(CourseDetail courseDetail)throws Exception;

    JsonResult editCourseDetail(CourseDetail courseDetail)throws Exception;

    JsonResult getTeacherCourseDetail(Long teacherId, Integer weekDay)throws Exception;

    JsonResult getCourseDetailById(Long id)throws Exception;
}
