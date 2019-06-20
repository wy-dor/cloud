package com.learning.cloud.courseWare.dao;

import com.learning.cloud.courseWare.entity.CourseWare;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CourseWareDao {
    int addCourseWare(CourseWare courseWare);
}
