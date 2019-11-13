package com.learning.cloud.excel.service;

import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.domain.JsonResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExportService {
    JsonResult downloadExcelGrade(Long moduleId, Integer classId) throws IOException;

    JsonResult downloadExcelByName(HttpServletResponse response, String filePath, String title) throws Exception;

    JsonResult exportExcelModule(GradeModule gradeModule) throws IOException;

    JsonResult downloadDutyRecordStatistics(Integer schoolId, String gradeName, String startTime, String endTime) throws IOException;
}
