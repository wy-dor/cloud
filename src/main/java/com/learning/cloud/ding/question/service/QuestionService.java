package com.learning.cloud.ding.question.service;

import com.learning.cloud.ding.question.entity.Answer;
import com.learning.cloud.ding.question.entity.Question;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yyt
 * @Date: 2019-03-21 17:55
 * @Desc:
 */
public interface QuestionService {
    JsonResult addQuestion(MultipartFile file, Question question)throws Exception;

    JsonResult getQuestion(Question question)throws Exception;

    JsonResult addAnswer(MultipartFile file, Answer answer)throws Exception;

    JsonResult getAnswer(Answer questionId)throws Exception;

    JsonResult deleteQuestion(Long id)throws Exception;

    JsonResult getQuestionIn(Question question)throws Exception;

    JsonResult closeQuestion(Long id)throws Exception;

    JsonResult deleteBatchQuestions(String questionIds);
}
