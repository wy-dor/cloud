package com.learning.cloud.score.service.impl;

import com.learning.cloud.score.dao.ScoreTypeDao;
import com.learning.cloud.score.entity.ScoreType;
import com.learning.cloud.score.service.ScoreTypeService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreTypeServiceImpl implements ScoreTypeService {

    @Autowired
    private ScoreTypeDao scoreTypeDao;

    @Override
    public JsonResult addScoreType(ScoreType scoreType) throws Exception {
        int i = scoreTypeDao.addScoreType(scoreType);
        return JsonResultUtil.success(scoreType.getId());
    }

    @Override
    public JsonResult editScoreType(ScoreType scoreType) throws Exception {
        int i = scoreTypeDao.editScoreType(scoreType);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
        }

    }

    @Override
    public JsonResult getOrganizeScoreType(ScoreType scoreType) throws Exception {
        List<ScoreType> scoreTypes = scoreTypeDao.getOrganizeScoreType(scoreType);
        return JsonResultUtil.success(new PageEntity<>(scoreTypes));
    }
}
