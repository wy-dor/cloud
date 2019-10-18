package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.CourseSection;
import com.learning.cloud.course.entity.CourseSectionArray;
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
 * @Desc: 节次，先增加主表，再加从表
 */
@RestController
public class CourseSectionController {

    @Autowired
    private CourseSectionService courseSectionService;

    //新增时令主表
    @PostMapping("/addSectionArray")
    public JsonResult addSectionArray(CourseSectionArray courseSectionArray)throws Exception{
        return courseSectionService.addSectionArray(courseSectionArray);
    }
    //保存时令主表
    @PostMapping("/saveSectionArray")
    public JsonResult saveSectionArray(@RequestParam(value="id",required = true) Long id,
                                       @RequestParam(value="name",required = true) String name)throws Exception{
        return courseSectionService.saveSectionArray(id,name);
    }

    //获取学校生效的时令主表
    @GetMapping("/getSchoolSectionArray")
    public JsonResult getSchoolSectionArray(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return courseSectionService.getSchoolSectionArray(schoolId);
    }

    //获取时令主表列表
    @GetMapping("/getSchoolSectionList")
    public JsonResult getSchoolSectionList(@RequestParam(value="schoolId",required = true) Long schoolId)throws Exception{
        return courseSectionService.getSchoolSectionList(schoolId);
    }

    //设置时令启用
    @PostMapping("/setSectionArrayActive")
    public JsonResult setSectionArrayActive(Long id,Long schoolId)throws Exception{
        return courseSectionService.setSectionArrayActive(id,schoolId);
    }

    //新增时令时间
    @PostMapping("/addSection")
    public JsonResult addSection(CourseSection courseSection)throws Exception{
        return courseSectionService.addSection(courseSection);
    }

    @PostMapping("/editSection")
    public JsonResult editSection(CourseSection courseSection)throws Exception{
        return courseSectionService.editSection(courseSection);
    }

    @GetMapping("/getSchoolSection")
    public JsonResult getSchoolSection(@RequestParam(value="id",required = true) Long id)throws Exception{
        return courseSectionService.getSchoolSection(id);
    }


    @PostMapping("/deleteSection")
    public JsonResult deleteSection(@RequestParam(value="id",required = true) Long id)throws Exception{
        return courseSectionService.deleteSection(id);
    }

    @PostMapping("/deleteSchoolSectionBySectionId")
    public JsonResult deleteSchoolSection(@RequestParam(value="sectionId",required = true) Long sectionId)throws Exception{
        return courseSectionService.deleteSchoolSection(sectionId);
    }



}
