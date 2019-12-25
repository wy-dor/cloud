package com.learning.cloud.workProcess.service;

import com.learning.cloud.workProcess.entity.CourseInstance;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019/11/14 11:22 上午
 * @Desc:
 */
public interface CourseExchangeProcessService {
    JsonResult createCourseExchangeProcess(String corpId) throws Exception;

    JsonResult createCourseExchangeProcessInstance(CourseInstance courseInstance) throws Exception;

    JsonResult getCourseProcess(String corpId) throws Exception;
}
