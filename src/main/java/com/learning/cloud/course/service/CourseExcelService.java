package com.learning.cloud.course.service;

import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yyt
 * @Date: 2019/10/15 10:13 上午
 * @Desc:
 */
public interface CourseExcelService {
    JsonResult exportCourseTemplet(String classIds, Long schoolId) throws Exception;

    JsonResult importCourseTemplet(MultipartFile file, Long schoolId) throws Exception;

    JsonResult getInfoBeforeImportCourse(Long schoolId, Long classId) throws Exception;
}
