package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.EvaluationItemDao;
import com.learning.cloud.evaluation.entity.EvaluationItem;
import com.learning.cloud.evaluation.service.EvaluationItemService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EvaluationItemServiceImpl implements EvaluationItemService {

    @Autowired
    private EvaluationItemDao evaluationItemDao;

    @Override
    public JsonResult addEvaluationItem(EvaluationItem evaluationItem) throws Exception {
        int i = evaluationItemDao.insert(evaluationItem);
        return JsonResultUtil.success("成功增加" + i + "条数据:id "+evaluationItem.getId());
    }

    @Override
    public JsonResult getEvaluationItem(EvaluationItem evaluationItem) {
        List<EvaluationItem> evaluationItemList = evaluationItemDao.getByItem(evaluationItem);
        return JsonResultUtil.success(new PageEntity<>(evaluationItemList));
    }

    @Override
    public JsonResult getEvaluationItemById(Long id) {
        EvaluationItem evaluationItem = evaluationItemDao.getById(id);
        return JsonResultUtil.success(evaluationItem);
    }

    @Override
    public JsonResult deleteEvaluationItemById(Long id) {
        EvaluationItem evaluationItem = new EvaluationItem();
        evaluationItem.setId(id);
        int i = evaluationItemDao.deleteByItem(evaluationItem);
        return JsonResultUtil.success("删除成功");
    }

    @Override
    public JsonResult updateEvaluationItem(EvaluationItem evaluationItem) throws Exception {
        int i = evaluationItemDao.update(evaluationItem);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

    @Override
    public JsonResult batchUpdateEvaluationItem(String ids, Integer status) {
        int i = 0;
        String[] split = ids.split(",");
        for (String s : split) {
            long id = Long.parseLong(s);
            EvaluationItem evaluationItem = new EvaluationItem();
            evaluationItem.setId(id);
            int j = evaluationItemDao.update(evaluationItem);
            if (j == 1) {
                i++;
            }
        }
        return JsonResultUtil.success("成功更新" + i + "条信息");
    }

}
