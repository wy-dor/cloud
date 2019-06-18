package com.learning.cloud.course.service;

import com.learning.cloud.course.entity.CourseExchange;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-06-13 16:09
 * @Desc:
 */
public interface CourseExchangeService {
    JsonResult addCourseExchange(CourseExchange courseExchange)throws Exception;

    JsonResult getCourseExchange(String day)throws Exception;

    JsonResult getMyExchange(Long teacherId)throws Exception;
}
