package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.EvaluationDimensionDao;
import com.learning.cloud.evaluation.dao.EvaluationItemDao;
import com.learning.cloud.evaluation.entity.EvaluationDimension;
import com.learning.cloud.evaluation.entity.EvaluationItem;
import com.learning.cloud.evaluation.service.EvaluationDimensionService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EvaluationDimensionServiceImpl implements EvaluationDimensionService {

    @Autowired
    private EvaluationDimensionDao dimensionDao;

    @Autowired
    private EvaluationItemDao itemDao;

    @Override
    public JsonResult addEvaluationDimension(EvaluationDimension evaluationDimension) {
        int i = dimensionDao.insert(evaluationDimension);
        return JsonResultUtil.success("成功增加" + i + "条数据  id:" + evaluationDimension.getId());
    }

    @Override
    public JsonResult getEvaluationDimensionById(Long id) {
        EvaluationDimension evaluationDimension = dimensionDao.getById(id);
        return JsonResultUtil.success(evaluationDimension);
    }

    @Override
    public JsonResult deleteEvaluationDimensionById(Long id) {
        EvaluationItem evaluationItem = new EvaluationItem();
        evaluationItem.setDimensionId(id);
        itemDao.deleteByItem(evaluationItem);
        dimensionDao.deleteById(id);
        return JsonResultUtil.success("删除成功");
    }

    @Override
    public JsonResult updateEvaluationDimension(EvaluationDimension evaluationDimension) {
        int i = dimensionDao.update(evaluationDimension);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

    @Override
    public JsonResult getEvaluationDimension(EvaluationDimension evaluationDimension) {
        List<EvaluationDimension> evaluationDimensionList = dimensionDao.getByDimension(evaluationDimension);
        return JsonResultUtil.success(new PageEntity<>(evaluationDimensionList));
    }


}
