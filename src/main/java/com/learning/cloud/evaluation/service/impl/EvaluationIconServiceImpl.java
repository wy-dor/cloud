package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.cloud.evaluation.dao.EvaluationIconDao;
import com.learning.cloud.evaluation.entity.EvaluationIcon;
import com.learning.cloud.evaluation.service.EvaluationIconService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class EvaluationIconServiceImpl implements EvaluationIconService {

    @Autowired
    private EvaluationIconDao evaluationIconDao;

    @Autowired
    private QuestionService questionService;

    @Override
    public JsonResult addEvaluationIcon(MultipartFile file, EvaluationIcon evaluationIcon) throws Exception {
        byte[] bytes = file.getBytes();
        if (bytes.length > 200 * 1024 * 8) {
            throw new Exception("图片大小限制200KB以内，请重新上传");
        }
        String s = questionService.base64Reduce(file);
        evaluationIcon.setPic(s);
        int i = evaluationIconDao.insert(evaluationIcon);
        Long id = evaluationIcon.getId();
        return JsonResultUtil.success("成功增加" + i + "条数据:id " + id);
    }

    @Override
    public JsonResult getEvaluationIcon(EvaluationIcon evaluationIcon) {
        List<EvaluationIcon> evaluationIconList = evaluationIconDao.getByIcon(evaluationIcon);
        return JsonResultUtil.success(new PageEntity<>(evaluationIconList));
    }

    @Override
    public EvaluationIcon getEvaluationIconById(Long id) {
        EvaluationIcon evaluationIcon = evaluationIconDao.getById(id);
        return evaluationIcon;
    }

    @Override
    public JsonResult deleteEvaluationIconById(Long id) {
        int i = evaluationIconDao.deleteById(id);
        if (i > 0) {
            return JsonResultUtil.success("删除成功");
        }
        return JsonResultUtil.success("删除失败");

    }

    @Override
    public JsonResult updateEvaluationIcon(EvaluationIcon evaluationIcon) throws Exception {
        int i = evaluationIconDao.update(evaluationIcon);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

}
