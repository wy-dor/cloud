package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.CourseSection;
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

    List<CourseSection> getSchoolSection(Long schoolId);

    int saveSectionArray(@Param("schoolId") Long schoolId, @Param("sectionArray") String sectionArray);
}
