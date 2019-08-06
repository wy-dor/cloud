package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.PreLesson;
import com.learning.cloud.course.service.PreLessonService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreLessonController {

    @Autowired
    private PreLessonService preLessonService;

    @PostMapping("/addPreLesson")
    public JsonResult addPreLesson(PreLesson preLesson){
        return preLessonService.addPreLesson(preLesson);
    }
}
