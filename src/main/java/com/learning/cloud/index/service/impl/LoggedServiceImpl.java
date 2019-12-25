package com.learning.cloud.index.service.impl;

import com.learning.cloud.index.dao.LoggedDao;
import com.learning.cloud.index.entity.LoggedRecord;
import com.learning.cloud.index.service.LoggedService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.cloud.score.entity.ScoreRecordSchool;
import com.learning.cloud.score.service.ScoreRecordService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-10-11 15:39
 * @Desc:
 */
@Service
@Slf4j
public class LoggedServiceImpl implements LoggedService {

    @Autowired
    private LoggedDao loggedDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private ScoreRecordService scoreRecordService;

    @Override
    public JsonResult addLoggedRecord(LoggedRecord loggedRecord) throws Exception {
        //插入登录记录，每个人一条，存在就更新最新时间

        int i = loggedDao.addLoggedRecord(loggedRecord);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    //获取学校每日登录人数，计算
    @Override
    public void AddSchoolScoreFormActivity() {
        List<School> schools = schoolDao.getSchools();
        //循环获取学校的登录比例
        for (School school : schools) {
            Integer activeUser = loggedDao.getActiveUserByCorpId(school.getCorpId());
            //按比例计算分值，加入到积分中。
            if (school.getOrgActiveUserCount() != null) {
                Integer all = school.getOrgActiveUserCount().intValue();
                Integer point = activeUser * 100 / all;//-128～127可以直接比较
                if (50 <= point && point < 60) {
                    point = 10;
                } else if (60 <= point && point < 70) {
                    point = 15;
                } else if (70 <= point && point < 80) {
                    point = 20;
                } else if (80 <= point && point < 90) {
                    point = 30;
                } else if (90 <= point && point < 100) {
                    point = 40;
                } else if (100 == point) {
                    point = 50;
                } else {
                    point = 5;
                }
                //插入积分
                ScoreRecordSchool scoreRecordSchool = new ScoreRecordSchool();
                scoreRecordSchool.setScore(point.longValue());
                scoreRecordSchool.setBureauId(school.getBureauId().longValue());
                scoreRecordSchool.setSchoolId(school.getId().longValue());
                scoreRecordSchool.setScoreTypeId(-1L);

                try {
                    scoreRecordService.addScoreRecordSchool(scoreRecordSchool);
                } catch (Exception e) {
                    log.error("插入学校活跃度积分报错");
                    e.printStackTrace();
                }
            }
        }
    }

}
