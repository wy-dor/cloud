package com.learning.cloud.course.controller;

import com.learning.cloud.course.service.CourseExcelService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.util.JAXBSource;
import java.io.IOException;

/**
 * @Author: yyt
 * @Date: 2019/10/15 9:37 上午
 * @Desc: 课表导出，导入
 */
@RestController
public class CourseExcelController {

    @Autowired
    private CourseExcelService courseExcelService;

    //下载课表
    @GetMapping("/exportCourseTemplet")
    public JsonResult exportCourseTemplet(String classIds, Long schoolId) throws Exception {
        return courseExcelService.exportCourseTemplet(classIds, schoolId);
    }

    @GetMapping("/getInfoBeforeImportCourse")
    public JsonResult getInfoBeforeImportCourse(@RequestParam("schoolId") Long schoolId, Long classId) throws Exception {
        return courseExcelService.getInfoBeforeImportCourse(schoolId, classId);
    }

    //上传课表
    @PostMapping("/importCourseTemplet")
    public JsonResult importCourseTemplet(@RequestParam("file") MultipartFile file, @RequestParam("schoolId") Long schoolId) throws Exception {
        return courseExcelService.importCourseTemplet(file, schoolId);
    }
}
