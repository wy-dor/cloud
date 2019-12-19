package com.learning.cloud.excel.controller;

import com.learning.cloud.excel.service.ExportService;
import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ExportController {

    @Autowired
    private ExportService exportService;

    //id,classesStr,subjects,title,scoringRoles
    @RequestMapping("/exportExcelModule")
    public JsonResult exportExcelModule(GradeModule gradeModule) throws Exception {
        return exportService.exportExcelModule(gradeModule);
    }

    @PostMapping("/downloadExcelGrade")
    public JsonResult downloadExcelGrade(Long moduleId, Integer classId) throws IOException {
        return exportService.downloadExcelGrade(moduleId, classId);
    }

    //根据名字和存储路径下载
    @RequestMapping("/downloadExcelByName")
    public JsonResult downloadExcelByName(HttpServletResponse response, String filePath, String title) throws Exception {
        return exportService.downloadExcelByName(response, filePath, title);
    }

    @GetMapping("/downloadDutyRecordStatistics")
    public JsonResult downloadDutyRecordStatistics(Integer schoolId, String gradeName, String startTime, String endTime) throws IOException {
        return exportService.downloadDutyRecordStatistics(schoolId, gradeName, startTime, endTime);
    }
}
