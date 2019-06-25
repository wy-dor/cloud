package com.learning.cloud.ding.question.dao;

import com.learning.cloud.ding.question.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-03-21 17:56
 * @Desc:
 */
@Repository
@Mapper
public interface QuestionDao {
    int addQuestion(Question question);

    List<Question> getQuestion(Question question);

    int deleteQuestion(Long id);

    List<Question> getQuestionImIN(Question question);

    void updateSmallPic(@Param("sPicId") Long sPicId, @Param("id") Long id);

    int closeQuestion(Long id);

}
