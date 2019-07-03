package com.learning.cloud.score.service.impl;

import com.learning.cloud.score.dao.ScoreRecordDao;
import com.learning.cloud.score.dao.ScoreTypeDao;
import com.learning.cloud.score.entity.SchoolScoreboard;
import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.cloud.score.entity.ScoreType;
import com.learning.cloud.score.service.ScoreRecordService;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreRecordServiceImpl implements ScoreRecordService {
    @Autowired
    private ScoreRecordDao scoreRecordDao;

    @Autowired
    private ScoreTypeDao scoreTypeDao;

    @Override
    public JsonResult addScoreRecord(ScoreRecord scoreRecord) throws Exception{
        // 获取用户最新的积分记录
        ScoreRecord last = scoreRecordDao.getLastScoreRecord(scoreRecord.getUserId());
        Long lastScore = new Long(0);
        if(last!=null){
            lastScore = last.getScore();
        }
        ScoreType scoreType = scoreTypeDao.getScoreTypeById(scoreRecord.getScoreTypeId());
        if(scoreType==null){
            return JsonResultUtil.error(JsonResultEnum.NO_SCORE_ACTION);
        }
        // 判断同一项每日加分次数
        List<ScoreRecord> scoreRecords = scoreRecordDao.getRecordTimeByType(scoreRecord.getScoreTypeId(),scoreRecord.getUserId());
        if(scoreRecords.size()>=scoreType.getTime()){
            return JsonResultUtil.error(JsonResultEnum.SCORE_TIME_LIMIT);
        }
        scoreRecord.setScore(lastScore+scoreType.getScore());
        int i = scoreRecordDao.addScoreRecord(scoreRecord);
        return JsonResultUtil.success(scoreRecord.getId());
    }

    @Override
    public JsonResult getUserScore(String userId) throws Exception {
        ScoreRecord last = scoreRecordDao.getLastScoreRecord(userId);
        return JsonResultUtil.success(last);
    }


}
