package com.learning.cloud.ding.question.controller;

import com.learning.cloud.ding.question.entity.Answer;
import com.learning.cloud.ding.question.entity.Question;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yyt
 * @Date: 2019-03-21 17:11
 * @Desc: 在线答疑
 */
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 新增问题
     */
    @PostMapping("/addQuestion")
    public JsonResult addQuestion(@RequestParam(value="file",required = false) MultipartFile file, Question question)throws Exception{
        return questionService.addQuestion(file,question);
    }

    /**
     * 展示问题
     */
    @GetMapping("/getQuestion")
    public JsonResult getQuestion(Question question)throws Exception{
        return questionService.getQuestion(question);
    }

    /*获取我参与的问题*/
    @GetMapping("/getQuestionIn")
    public JsonResult getQuestionIn(Question question)throws Exception{
        return questionService.getQuestionIn(question);
    }

    /**
     * 删除问题
     */
    @PostMapping("/deleteQuestion")
    public JsonResult deleteQuestion(@RequestParam(value="id") Long id)throws Exception{
        return questionService.deleteQuestion(id);
    }

    /*批量删除问题*/
    @PostMapping("/deleteBatchQuestions")
    public JsonResult deleteBatchQuestions(String questionIds){
        return questionService.deleteBatchQuestions(questionIds);
    }

    /*关闭问题*/
    @GetMapping("/closeQuestion")
    public JsonResult closeQuestion(@RequestParam(value="id") Long id)throws Exception{
        return questionService.closeQuestion(id);
    }


    /**
     * 回答问题
     */
    @PostMapping("/addAnswer")
    public JsonResult addAnswer(@RequestParam(value="file", required = false) MultipartFile file, Answer answer)throws Exception{
        return questionService.addAnswer(file, answer);
    }

    /**
     * 获取问题的回到 主要参数questionId
     */
    @GetMapping("/getAnswer")
    public JsonResult getAnswer(Answer answer)throws Exception{
        return questionService.getAnswer(answer);
    }

}
