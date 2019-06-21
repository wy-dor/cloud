package com.learning.cloud.ding.question.dao;

import com.learning.cloud.ding.question.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-03-22 14:36
 * @Desc:
 */
@Repository
@Mapper
public interface AnswerDao {
    int addAnswer(Answer answer);

    List<Answer> getAnswer(Long questionId);
}
