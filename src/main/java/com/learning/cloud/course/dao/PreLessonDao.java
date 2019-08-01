package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.PreLesson;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PreLessonDao {
    int insert(PreLesson record);
}
