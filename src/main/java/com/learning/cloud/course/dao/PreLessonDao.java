package com.learning.cloud.course.dao;

import com.learning.cloud.course.entity.PreLesson;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PreLessonDao {
    int insert(PreLesson record);

    int deletePreLesson(Long id);

    PreLesson getPreLessonById(Long id);

    List<PreLesson> getAllPreLessons(PreLesson preLesson);
}
