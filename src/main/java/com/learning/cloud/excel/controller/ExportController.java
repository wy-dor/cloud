package com.learning.cloud.excel.controller;

import com.learning.cloud.excel.service.ExportService;
import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ExportController {

    @Autowired
    private ExportService exportService;

    //id,classesStr,subjects,title
    @RequestMapping("/exportExcelModule")
    public JsonResult exportExcelModule(GradeModule gradeModule)throws Exception{
        return exportService.exportExcelModule(gradeModule);
    }

    @PostMapping("/downloadExcelGrade")
    public JsonResult downloadExcelGrade(HttpServletResponse response, Long moduleId) throws IOException{
        return exportService.downloadExcelGrade(response,moduleId);
    }
    // 下载账单
    @RequestMapping("/downloadExcelByName")
    public JsonResult downloadExcelByName(HttpServletResponse response, String filePath, String title) throws Exception{
        return exportService.downloadExcelByName(response, filePath, title);
    }
}
