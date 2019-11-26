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

    JsonResult getCourseExchange(Long classId, String day);

    JsonResult getMyExchange(Long teacherId, Integer status)throws Exception;

    JsonResult confirmExchange(CourseExchange courseExchange)throws Exception;

    void renewCourseExchangeStatus(CourseExchange courseExchange) throws Exception;
}
