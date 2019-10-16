package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.EvaluationGroupDao;
import com.learning.cloud.evaluation.entity.EvaluationGroup;
import com.learning.cloud.evaluation.entity.StuInfo;
import com.learning.cloud.evaluation.service.EvaluationGroupService;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EvaluationGroupServiceImpl implements EvaluationGroupService {

    @Autowired
    private EvaluationGroupDao evaluationGroupDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public JsonResult addEvaluationGroup(EvaluationGroup evaluationGroup) {
        int i = evaluationGroupDao.insert(evaluationGroup);
        return JsonResultUtil.success("成功增加" + i + "条数据:id "+evaluationGroup.getId());
    }

    @Override
    public JsonResult getEvaluationGroup(EvaluationGroup evaluationGroup) {
        List<EvaluationGroup> evaluationGroupList = evaluationGroupDao.getByGroup(evaluationGroup);
        for (EvaluationGroup group : evaluationGroupList) {
            setStuList(group);
        }
        return JsonResultUtil.success(new PageEntity<>(evaluationGroupList));
    }

    @Override
    public JsonResult getEvaluationGroupById(Long id) {
        EvaluationGroup evaluationGroup = evaluationGroupDao.getById(id);
        setStuList(evaluationGroup);
        return JsonResultUtil.success(evaluationGroup);
    }

    public void setStuList(EvaluationGroup evaluationGroup) {
        String studentUserIds = evaluationGroup.getStudentUserIds();
        List<StuInfo> stuInfoList = new ArrayList<>();
        String[] split = studentUserIds.split(",");
        for (String s : split) {
            Student byUserId = studentDao.getByUserId(s);
            if(byUserId != null){
                StuInfo stuInfo = new StuInfo();
                stuInfo.setStuUserId(s);
                stuInfo.setStuName(byUserId.getStudentName());
                stuInfo.setStuNo(byUserId.getStudentNo());
                stuInfoList.add(stuInfo);
            }
        }
        evaluationGroup.setStuList(stuInfoList);
    }

    @Override
    public JsonResult deleteEvaluationGroupById(Long id) {
        EvaluationGroup evaluationGroup = new EvaluationGroup();
        evaluationGroup.setId(id);
        int i = evaluationGroupDao.deleteByGroup(evaluationGroup);
        return JsonResultUtil.success("删除成功");
    }

    @Override
    public JsonResult updateEvaluationGroup(EvaluationGroup evaluationGroup) throws Exception {
        int i = evaluationGroupDao.update(evaluationGroup);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

    @Override
    public JsonResult batchUpdateEvaluationGroup(String ids, Integer status) {
        int i = 0;
        String[] split = ids.split(",");
        for (String s : split) {
            long id = Long.parseLong(s);
            EvaluationGroup evaluationGroup = new EvaluationGroup();
            evaluationGroup.setId(id);
            int j = evaluationGroupDao.update(evaluationGroup);
            if (j == 1) {
                i++;
            }
        }
        return JsonResultUtil.success("成功更新" + i + "条信息");
    }

}
