package com.learning.cloud.course.service;

import com.learning.cloud.course.entity.TeacherCourse;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yyt
 * @Date: 2019/11/11 10:53 上午
 * @Desc:
 */
public interface TeacherCourseService {

    JsonResult importTeacherCourse(MultipartFile file, Long schoolId)throws Exception;

    JsonResult getTeacherCourseTemplet(Long schoolId)throws Exception;
}
