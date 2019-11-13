package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.TeacherCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019/11/11 11:36 上午
 * @Desc:
 */
@Mapper
@Repository
public interface TeacherCourseDao {
    List<TeacherCourse> getTeacherCourseByClassId(Integer classId);

    int setTeacherCourseTypeEveryClass(TeacherCourse teacherCourse);
}
