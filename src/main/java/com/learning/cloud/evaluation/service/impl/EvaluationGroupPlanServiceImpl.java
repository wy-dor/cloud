package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.EvaluationGroupPlanDao;
import com.learning.cloud.evaluation.dao.EvaluationGroupDao;
import com.learning.cloud.evaluation.entity.EvaluationGroupPlan;
import com.learning.cloud.evaluation.entity.EvaluationGroup;
import com.learning.cloud.evaluation.entity.StuInfo;
import com.learning.cloud.evaluation.service.EvaluationGroupPlanService;
import com.learning.cloud.user.student.dao.StudentDao;
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
public class EvaluationGroupPlanServiceImpl implements EvaluationGroupPlanService {

    @Autowired
    private EvaluationGroupPlanDao evaluationGroupPlanDao;

    @Autowired
    private EvaluationGroupDao evaluationGroupDao;

    @Autowired
    private StudentDao studentDao;

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
        //班级下所有学生
        Integer classId = evaluationGroupPlan.getClassId();
        List<StuInfo> classStuInfoList = studentDao.listStuInfoInClass(classId);

        //小组信息
        List<EvaluationGroupPlan> evaluationGroupPlanList = evaluationGroupPlanDao.getByGroupPlan(evaluationGroupPlan);
        for (EvaluationGroupPlan groupPlan : evaluationGroupPlanList) {
            Long id = groupPlan.getId();
            EvaluationGroup evaluationGroup = new EvaluationGroup();
            evaluationGroup.setGroupPlanId(id);
            List<EvaluationGroup> groupList = evaluationGroupDao.getByGroup(evaluationGroup);
            List<String> stuUserIdListInGroup  = new ArrayList<>();
            for (EvaluationGroup group : groupList) {
                String studentUserIds = group.getStudentUserIds();
                String[] split = studentUserIds.split(",");
                for (String s : split) {
                    stuUserIdListInGroup.add(s);
                }
            }
            groupPlan.setGroupList(groupList);

            //返回未处理学生信息
            List<StuInfo> stuInfoList = new ArrayList<>();
            for (StuInfo stuInfo : classStuInfoList) {
                for (String userId : stuUserIdListInGroup) {
                    if(stuInfo.getUserId() == userId){
                        continue;
                    }
                    stuInfoList.add(stuInfo);
                }
            }

            groupPlan.setStuInfoList(stuInfoList);
        }
        return JsonResultUtil.success(new PageEntity<>(evaluationGroupPlanList));
    }

}
