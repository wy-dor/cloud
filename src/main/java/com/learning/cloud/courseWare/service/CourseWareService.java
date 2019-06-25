package com.learning.cloud.courseWare.service;

import com.learning.cloud.courseWare.entity.CourseWare;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface CourseWareService {
    JsonResult addCourseWare(CourseWare courseWare, MultipartFile file)throws Exception;

    JsonResult getCourseWare(String day, Long cdId)throws Exception;
}
