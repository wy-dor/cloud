package com.learning.cloud.course.controller;

import com.learning.cloud.course.entity.PreLesson;
import com.learning.cloud.course.service.PreLessonService;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PreLessonController {

    @Autowired
    private PreLessonService preLessonService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/addPreLesson")
    public JsonResult addPreLesson(@RequestParam(value = "picFiles",required = false) MultipartFile[] picFiles,
                                   /*@RequestParam(value = "fileList",required = false) List<MultipartFile> fileList,*/
                                   PreLesson preLesson) throws Exception {
        List<Long> ids = new ArrayList<>();
        if(picFiles != null && picFiles.length != 0){
            for (int i = 0; i < picFiles.length; i++) {
                Long picFileId = questionService.reduceImg(picFiles[i]);
                ids.add(picFileId);
            }
            preLesson.setTeachingPicIds(ids.toString());
        }
        return preLessonService.addPreLesson(preLesson);
    }

    @GetMapping("/deletePreLesson")
    public JsonResult deletePreLesson(Long id){
        return preLessonService.deletePreLesson(id);
    }

    @GetMapping("/getPreLessonById")
    public JsonResult getPreLessonById(Long id){
        return preLessonService.getPreLessonById(id);
    }

    @GetMapping("/getAllPreLessons")
    public JsonResult getAllPreLessons(PreLesson preLesson){
        return preLessonService.getAllPreLessons(preLesson);
    }

}
