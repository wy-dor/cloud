package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.CourseType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-10 16:52
 * @Desc:
 */
@Repository
@Mapper
public interface CourseTypeDao {
    List<CourseType> getSchoolCourseType(Long schoolId);

    int addSchoolCourseType(CourseType courseType);

    int deleteCourseType(Long id);
}
