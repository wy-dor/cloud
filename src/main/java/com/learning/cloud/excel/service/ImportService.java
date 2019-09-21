package com.learning.cloud.excel.service;

import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {
    JsonResult importExcelGradeEntries(MultipartFile file, Long moduleId) throws Exception;
}
