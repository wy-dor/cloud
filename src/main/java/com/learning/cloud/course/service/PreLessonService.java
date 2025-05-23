package com.learning.cloud.course.service;

import com.learning.cloud.course.entity.PreLesson;
import com.learning.domain.JsonResult;

public interface PreLessonService {
    JsonResult addPreLesson(PreLesson preLesson);

    JsonResult deletePreLesson(Long id);

    JsonResult getPreLessonById(Long id);

    JsonResult getAllPreLessons(PreLesson preLesson);
}
