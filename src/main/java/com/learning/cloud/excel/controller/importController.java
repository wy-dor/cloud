package com.learning.cloud.excel.controller;

import com.learning.cloud.excel.service.ImportService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class importController {

    @Autowired
    private ImportService importService;

    @PostMapping("/importExcelGradeEntries")
    public JsonResult importExcelGradeEntries(@RequestParam("file") MultipartFile file, @RequestParam("moduleId") Long moduleId) throws Exception{
        return importService.importExcelGradeEntries(file,moduleId);
    }
}
