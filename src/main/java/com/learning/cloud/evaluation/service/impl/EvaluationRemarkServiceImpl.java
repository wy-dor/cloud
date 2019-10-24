package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.EvaluationRemarkDao;
import com.learning.cloud.evaluation.entity.EvaluationRemark;
import com.learning.cloud.evaluation.service.EvaluationRemarkService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EvaluationRemarkServiceImpl implements EvaluationRemarkService {

    @Autowired
    private EvaluationRemarkDao remarkDao;

    @Override
    public JsonResult addEvaluationRemark(EvaluationRemark evaluationRemark) {
        int i = remarkDao.insert(evaluationRemark);
        return JsonResultUtil.success("成功增加" + i + "条数据:id " + evaluationRemark.getId());
    }

    @Override
    public JsonResult getEvaluationRemark(EvaluationRemark evaluationRemark) {
        List<EvaluationRemark> evaluationRemarkList = remarkDao.getByRemark(evaluationRemark);
        return JsonResultUtil.success(new PageEntity<>(evaluationRemarkList));
    }

    @Override
    public JsonResult getEvaluationRemarkById(Long id) {
        EvaluationRemark evaluationRemark = remarkDao.getById(id);
        return JsonResultUtil.success(evaluationRemark);
    }

    @Override
    public JsonResult deleteEvaluationRemarkById(Long id) {
        EvaluationRemark evaluationRemark = new EvaluationRemark();
        evaluationRemark.setId(id);
        int i = remarkDao.deleteByRemark(evaluationRemark);
        return JsonResultUtil.success("删除成功");
    }

    @Override
    public JsonResult updateEvaluationRemark(EvaluationRemark evaluationRemark) throws Exception {
        int i = remarkDao.update(evaluationRemark);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

}
