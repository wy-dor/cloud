package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-11 16:52
 * @Desc:
 */
@Repository
@Mapper
public interface CourseDao {
    Course getCourseByClassId(Long classId);

    int addCourse(Course course);

    int editCourse(Course course);

    List<Course> getSchoolCourse(Course course);

    int publishCourse(Long id);
}
