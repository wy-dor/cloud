package com.learning.cloud.score.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: yyt
 * @Date: 2019-09-22 15:20
 * @Desc: 积分排名查询传值
 */
@Data
public class ScoreRank extends BaseEntity {
    private Long bureauId;
    private Long schoolId;
    private String schoolName;
    private String name;
    private Long classId;
    private Long score;
    private String avatar;
    //班级排行查询
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date day;
}
