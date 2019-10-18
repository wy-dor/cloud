package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.CourseSection;
import com.learning.cloud.course.entity.CourseSectionArray;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-12 16:01
 * @Desc:
 */
@Repository
@Mapper
public interface CourseSectionDao {
    int addSection(CourseSection courseSection);

    int editSection(CourseSection courseSection);

    List<CourseSection> getSchoolSection(Long id);

    int saveSectionArray(@Param("id") Long id, @Param("sectionArray") String sectionArray, @Param("name") String name);

    int deleteSection(Long id);

    int deleteSchoolSection(Long sectionId);

    CourseSectionArray getSchoolSectionArray(Long schoolId);

    int setSectionArrayBlock(@Param("id") Long id, @Param("schoolId") Long schoolId);

    int setSectionArrayActive(Long id);

    List<CourseSectionArray> getSchoolSectionList(Long schoolId);

    int addSectionArray(CourseSectionArray courseSectionArray);
}
