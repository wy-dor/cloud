package com.learning.cloud.courseWare.service;

import com.learning.cloud.courseWare.entity.CourseWare;
import com.learning.domain.JsonResult;

public interface CourseWareService {
    JsonResult addCourseWare(CourseWare courseWare)throws Exception;
}
