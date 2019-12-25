package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.EvaluationGroupDao;
import com.learning.cloud.evaluation.entity.EvaluationGroup;
import com.learning.cloud.evaluation.entity.EvaluationGroupPlan;
import com.learning.cloud.evaluation.entity.StuInfo;
import com.learning.cloud.evaluation.service.EvaluationGroupService;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EvaluationGroupServiceImpl implements EvaluationGroupService {

    @Autowired
    private EvaluationGroupDao groupDao;

    @Autowired
    private StudentDao studentDao;

    //添加小组时需要将移动的组员在对应组中的人员信息删除
    @Override
    public JsonResult addEvaluationGroup(EvaluationGroup evaluationGroup) {
        String studentUserIds = evaluationGroup.getStudentUserIds();
        String[] split = studentUserIds.split(",");
        EvaluationGroup eg = new EvaluationGroup();
        eg.setGroupPlanId(evaluationGroup.getGroupPlanId());
        //将同一方案中其他组相同人员信息移除
        for (String s : split) {
            eg.setStudentUserIds(s);
            List<EvaluationGroup> byGroup = groupDao.getByGroup(eg);
            for (EvaluationGroup group : byGroup) {
                String userIds = group.getStudentUserIds();
                String concatUserIds = "," + userIds + ",";
                String replace = concatUserIds.replace(s + ",", "");
                String substring = "";
                //需考虑最后只剩下","时subString会报错
                if (!replace.equals(",")) {
                    substring = replace.substring(1, replace.lastIndexOf(","));
                }
                group.setStudentUserIds(substring);
                groupDao.update(group);
            }
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        evaluationGroup.setCreateTime(format);
        int i = groupDao.insert(evaluationGroup);
        return JsonResultUtil.success("成功增加" + i + "条数据:id " + evaluationGroup.getId());
    }

    @Override
    public List<EvaluationGroup> getEvaluationGroup(EvaluationGroup evaluationGroup) {
        List<EvaluationGroup> evaluationGroupList = groupDao.getByGroup(evaluationGroup);
        for (EvaluationGroup group : evaluationGroupList) {
            setStuList(group);
        }
        return evaluationGroupList;
    }

    @Override
    public JsonResult getEvaluationGroupById(Long id) {
        EvaluationGroup evaluationGroup = groupDao.getById(id);
        setStuList(evaluationGroup);
        return JsonResultUtil.success(evaluationGroup);
    }

    public void setStuList(EvaluationGroup evaluationGroup) {
        String studentUserIds = evaluationGroup.getStudentUserIds();
        List<StuInfo> stuInfoList = new ArrayList<>();
        String[] split = studentUserIds.split(",");
        if (split.length > 0) {
            for (String s : split) {
                StuInfo stuInfo = studentDao.getStuInfoByUserId(s);
                if (stuInfo != null) {
                    stuInfoList.add(stuInfo);
                }
            }
        }
        evaluationGroup.setStuList(stuInfoList);
    }

    @Override
    public JsonResult deleteEvaluationGroupById(Long id) {
        EvaluationGroup evaluationGroup = new EvaluationGroup();
        evaluationGroup.setId(id);
        int i = groupDao.deleteByGroup(evaluationGroup);
        return JsonResultUtil.success("删除成功");
    }

    @Override
    public JsonResult updateEvaluationGroup(EvaluationGroup evaluationGroup) throws Exception {
        int i = groupDao.update(evaluationGroup);
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
            int j = groupDao.update(evaluationGroup);
            if (j == 1) {
                i++;
            }
        }
        return JsonResultUtil.success("成功更新" + i + "条信息");
    }

    @Override
    public JsonResult getEvaluationGroupScoreList(EvaluationGroupPlan evaluationGroupPlan) {
        List<EvaluationGroup> evaluationGroupScoreList = groupDao.getEvaluationGroupScoreList(evaluationGroupPlan);
        for (EvaluationGroup evaluationGroup : evaluationGroupScoreList) {
            setStuList(evaluationGroup);
        }
        return JsonResultUtil.success(new PageEntity<>(evaluationGroupScoreList));
    }

}
