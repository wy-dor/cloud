package com.learning.cloud.ding.question.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: yyt
 * @Date: 2019-03-22 14:17
 * @Desc:
 */
@Data
public class Answer {
    private Long id;
    private Long questionId;
    private String answerUserId;
    private String answerName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private String answerTime;
    private String answerContent;
    private Long picId;
}
