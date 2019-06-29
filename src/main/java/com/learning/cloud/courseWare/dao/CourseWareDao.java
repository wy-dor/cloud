package com.learning.cloud.courseWare.dao;

import com.learning.cloud.courseWare.entity.CourseWare;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseWareDao {
    int addCourseWare(CourseWare courseWare);

    List<CourseWare> getCourseWare(@Param("day") String day, @Param("cdId") Long cdId);

    int likeThisCourseWare(Long id);

    List<CourseWare> getMyCourseWare(Long teacherId);
}
