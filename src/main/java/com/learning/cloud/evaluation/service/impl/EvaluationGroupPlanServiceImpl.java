package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.EvaluationGroupPlanDao;
import com.learning.cloud.evaluation.dao.EvaluationGroupDao;
import com.learning.cloud.evaluation.entity.EvaluationGroupPlan;
import com.learning.cloud.evaluation.entity.EvaluationGroup;
import com.learning.cloud.evaluation.service.EvaluationGroupPlanService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EvaluationGroupPlanServiceImpl implements EvaluationGroupPlanService {

    @Autowired
    private EvaluationGroupPlanDao evaluationGroupPlanDao;

    @Autowired
    private EvaluationGroupDao evaluationGroupDao;

    @Override
    public JsonResult addEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan) {
        int i = evaluationGroupPlanDao.insert(evaluationGroupPlan);
        return JsonResultUtil.success("成功增加" + i + "条数据  id:" + evaluationGroupPlan.getId());
    }

    @Override
    public JsonResult getEvaluationGroupPlanById(Long id) {
        EvaluationGroupPlan evaluationGroupPlan = evaluationGroupPlanDao.getById(id);
        return JsonResultUtil.success(evaluationGroupPlan);
    }

    @Override
    public JsonResult deleteEvaluationGroupPlanById(Long id) {
        EvaluationGroup evaluationGroup = new EvaluationGroup();
        evaluationGroup.setGroupPlanId(id);
        evaluationGroupDao.deleteByGroup(evaluationGroup);
        evaluationGroupPlanDao.deleteById(id);
        return JsonResultUtil.success("删除成功");
    }

    @Override
    public JsonResult updateEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan) {
        int i = evaluationGroupPlanDao.update(evaluationGroupPlan);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

    @Override
    public JsonResult getEvaluationGroupPlan(EvaluationGroupPlan evaluationGroupPlan) {
        List<EvaluationGroupPlan> evaluationGroupPlanList = evaluationGroupPlanDao.getByGroupPlan(evaluationGroupPlan);
        return JsonResultUtil.success(new PageEntity<>(evaluationGroupPlanList));
    }

}
