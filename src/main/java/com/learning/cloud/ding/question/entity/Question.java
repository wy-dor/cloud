package com.learning.cloud.ding.question.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: yyt
 * @Date: 2019-03-21 17:13
 * @Desc:
 */
@Data
public class Question extends BaseEntity {

    private Long id;
    private String createUserId;
    private String createName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private String createTime;
    private String question;
    private Long picId;
    private Integer type; //科目
    private Integer status;
    private Integer answerNum;// 回答条数
    private String answerUserId;
    private Long sPicId;

}
