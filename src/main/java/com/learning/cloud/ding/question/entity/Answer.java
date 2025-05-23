package com.learning.cloud.ding.question.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: yyt
 * @Date: 2019-03-22 14:17
 * @Desc:
 */
@Data
public class Answer extends BaseEntity {
    private Long id;
    private Long questionId;
    private String answerUserId;
    private String answerName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String answerTime;
    private String answerContent;
    private Long picId;
}
