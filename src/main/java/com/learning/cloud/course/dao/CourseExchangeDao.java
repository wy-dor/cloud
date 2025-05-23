package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.CourseExchange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

    List<CourseExchange> getCourseExchange(@Param("classId") Long classId, @Param("day") String day);

    List<CourseExchange> getMyExchange(@Param("teacherId") Long teacherId, @Param("day") String day, Integer status);

    int confirmExchange(CourseExchange courseExchange);

    CourseExchange getByInstanceId(String processInstanceId);

    CourseExchange getById(Long id);

    List<CourseExchange> listCourseExchangeForRenew(CourseExchange courseExchange);

    int update(CourseExchange courseExchange);
}
