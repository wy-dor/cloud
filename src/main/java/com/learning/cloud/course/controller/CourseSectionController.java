package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.CourseSection;
import com.learning.cloud.course.service.CourseSectionService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-06-12 15:04
 * @Desc: 节次
 */
@RestController
public class CourseSectionController {

    @Autowired
    private CourseSectionService courseSectionService;

    @PostMapping("/api/addSection")
    public JsonResult addSection(CourseSection courseSection)throws Exception{
        return courseSectionService.addSection(courseSection);
    }

    @PostMapping("/api/editSection")
    public JsonResult editSection(CourseSection courseSection)throws Exception{
        return courseSectionService.editSection(courseSection);
    }

    @GetMapping("/api/getSchoolSection")
    public JsonResult getSchoolSection(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return courseSectionService.getSchoolSection(schoolId);
    }

    @PostMapping("/api/saveSectionArray")
    public JsonResult saveSectionArray(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return courseSectionService.saveSectionArray(schoolId);
    }

    @PostMapping("/api/deleteSection")
    public JsonResult deleteSection(@RequestParam(value="id",required = true) Long id)throws Exception{
        return courseSectionService.deleteSection(id);
    }

    @PostMapping("/api/deleteSchoolSection")
    public JsonResult deleteSchoolSection(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return courseSectionService.deleteSchoolSection(schoolId);
    }

    @GetMapping("/api/getSchoolSectionArray")
    public JsonResult getSchoolSectionArray(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return courseSectionService.getSchoolSectionArray(schoolId);
    }

}
