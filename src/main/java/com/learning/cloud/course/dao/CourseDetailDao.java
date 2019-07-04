package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.CourseDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-12 19:26
 * @Desc:
 */
@Repository
@Mapper
public interface CourseDetailDao {
    List<CourseDetail> getCourseDetailByClassId(@Param("classId") Long classId, @Param("weekDay") Integer weekDay);

    int addCourseDetail(CourseDetail courseDetail);

    int editCourseDetail(CourseDetail courseDetail);

    List<CourseDetail> getTeacherCourseDetail(@Param("teacherId") Long teacherId, @Param("weekDay") Integer weekDay);

    CourseDetail getCourseDetailById(Long id);

    int deleteCourseDetailById(Long id);

    int deleteAllCourseDetail(Long courseId);
}
