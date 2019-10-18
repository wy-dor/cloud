package com.learning.cloud.course.service;

import com.learning.cloud.course.entity.CourseSection;
import com.learning.cloud.course.entity.CourseSectionArray;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-06-12 15:08
 * @Desc:
 */
public interface CourseSectionService {
    JsonResult addSection(CourseSection courseSection)throws Exception;

    JsonResult editSection(CourseSection courseSection)throws Exception;

    JsonResult getSchoolSection(Long id)throws Exception;

    JsonResult saveSectionArray(Long id, String name)throws Exception;

    JsonResult deleteSection(Long id)throws Exception;

    JsonResult deleteSchoolSection(Long sectionId)throws Exception;

    JsonResult getSchoolSectionArray(Long schoolId)throws Exception;

    JsonResult setSectionArrayActive(Long id, Long schoolId)throws Exception;

    JsonResult getSchoolSectionList(Long schoolId)throws Exception;

    JsonResult addSectionArray(CourseSectionArray courseSectionArray)throws Exception;
}
