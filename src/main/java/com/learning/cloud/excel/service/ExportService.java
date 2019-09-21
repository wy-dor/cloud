package com.learning.cloud.excel.service;

import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.domain.JsonResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExportService {
    JsonResult downloadExcelByName(HttpServletResponse response, String filePath, String title) throws Exception;

    JsonResult exportExcelModule(HttpServletResponse response, GradeModule gradeModule) throws IOException;

    JsonResult downloadExcelGrade(HttpServletResponse response, Long moduleId) throws IOException;
}
