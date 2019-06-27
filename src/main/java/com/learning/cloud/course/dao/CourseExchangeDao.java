package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.CourseExchange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-13 16:10
 * @Desc:
 */
@Repository
@Mapper
public interface CourseExchangeDao {
    int addCourseExchange(CourseExchange courseExchange);

    List<CourseExchange> getCourseExchange(String day);

    List<CourseExchange> getMyExchange(@Param("teacherId") Long teacherId, @Param("day") String day);

    int confirmExchange(@Param("id") Long id, @Param("status") int status);
}
