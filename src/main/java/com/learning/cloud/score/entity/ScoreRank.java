package com.learning.cloud.score.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: yyt
 * @Date: 2019-09-22 15:20
 * @Desc: 积分排名查询传值
 */
@Data
public class ScoreRank extends BaseEntity {
    private Long bureauId;
    private Long schoolId;
    private Long classId;
}
