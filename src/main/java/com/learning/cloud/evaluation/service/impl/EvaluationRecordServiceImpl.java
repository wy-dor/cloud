package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.EvaluationRemarkDao;
import com.learning.cloud.evaluation.dao.EvaluationRecordDao;
import com.learning.cloud.evaluation.dao.EvaluationScoreDao;
import com.learning.cloud.evaluation.entity.EvaluationRemark;
import com.learning.cloud.evaluation.entity.EvaluationRecord;
import com.learning.cloud.evaluation.entity.EvaluationScore;
import com.learning.cloud.evaluation.service.EvaluationRecordService;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EvaluationRecordServiceImpl implements EvaluationRecordService {

    @Autowired
    private EvaluationRecordDao recordDao;

    @Autowired
    private EvaluationRemarkDao remarkDao;

    @Autowired
    private EvaluationScoreDao scoreDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public JsonResult addEvaluationRecord(EvaluationRecord evaluationRecord) {
        Integer addingWay = evaluationRecord.getAddingWay();
        //按用户添加
        if(addingWay == 1){
            String userIds = evaluationRecord.getStudentUserIds();
            String[] split = userIds.split(",");
            for (String s : split) {
                EvaluationScore byUserId = scoreDao.getByUserId(s);
                if(byUserId == null){

                }else{

                }
            }
        }else if(addingWay == 2){
            String groupIds = evaluationRecord.getGroupIds();

        }
        int i = recordDao.insert(evaluationRecord);
        return JsonResultUtil.success("成功增加" + i + "条数据  id:" + evaluationRecord.getId());
    }

    @Override
    public JsonResult getEvaluationRecordById(Long id) {
        EvaluationRecord evaluationRecord = recordDao.getById(id);
        return JsonResultUtil.success(evaluationRecord);
    }

    @Override
    public JsonResult deleteEvaluationRecordById(Long id) {
        EvaluationRemark evaluationRemark = new EvaluationRemark();
        evaluationRemark.setRecordId(id);
        remarkDao.deleteByRemark(evaluationRemark);
        recordDao.deleteById(id);
        return JsonResultUtil.success("删除成功");
    }

    @Override
    public JsonResult updateEvaluationRecord(EvaluationRecord evaluationRecord) {
        int i = recordDao.update(evaluationRecord);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

    @Override
    public JsonResult getEvaluationRecord(EvaluationRecord evaluationRecord) {
        List<EvaluationRecord> evaluationRecordList = recordDao.getByRecord(evaluationRecord);
        return JsonResultUtil.success(new PageEntity<>(evaluationRecordList));
    }

}
