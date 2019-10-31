package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.evaluation.dao.*;
import com.learning.cloud.evaluation.entity.*;
import com.learning.cloud.evaluation.service.EvaluationRecordService;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class EvaluationRecordServiceImpl implements EvaluationRecordService {

    @Autowired
    private EvaluationRecordDao recordDao;

    @Autowired
    private EvaluationRemarkDao remarkDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private EvaluationGroupDao groupDao;

    @Autowired
    private EvaluationItemDao itemDao;

    @Autowired
    private EvaluationDimensionDao dimensionDao;

    @Override
    public JsonResult addEvaluationRecord(EvaluationRecord evaluationRecord) {
        saveRecord(evaluationRecord);
        int i = recordDao.insert(evaluationRecord);
        return JsonResultUtil.success(evaluationRecord.getId());
    }

    public void saveRecord(EvaluationRecord evaluationRecord) {
        Integer addingWay = evaluationRecord.getAddingWay();
        //按用户添加
        BigDecimal score = evaluationRecord.getScore();
        if (addingWay == 1) {
            String userIds = evaluationRecord.getStudentUserIds();
            saveScoreByUser(score, userIds);
        } else if (addingWay == 2) {
            String groupIds = evaluationRecord.getGroupIds();
            String[] split = groupIds.split(",");
            for (String s : split) {
                EvaluationGroup byId = groupDao.getById(Long.parseLong(s));
                BigDecimal totalScore = byId.getTotalScore();
                byId.setTotalScore(score.add(totalScore));
                groupDao.update(byId);
                String userIds = byId.getStudentUserIds();
                saveScoreByUser(score, userIds);
            }
        }
    }

    public void saveScoreByUser(BigDecimal score, String userIds) {
        String[] split = userIds.split(",");
        for (String s : split) {
            Student byUserId = studentDao.getByUserId(s);
            BigDecimal totalScore = byUserId.getTotalScore();
            byUserId.setTotalScore(totalScore.add(score));
            studentDao.update(byUserId);
        }
    }

    @Override
    public JsonResult getEvaluationRecordById(Long id) {
        EvaluationRecord evaluationRecord = recordDao.getById(id);
        return JsonResultUtil.success(evaluationRecord);
    }

    @Override
    public JsonResult deleteEvaluationRecordById(Long id) {
        EvaluationRecord byId = recordDao.getById(id);
        BigDecimal score = byId.getScore();
        BigDecimal minusScore = score.multiply(new BigDecimal(-1));
        byId.setScore(minusScore);
        //删除相应评论
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
        for (EvaluationRecord record : evaluationRecordList) {
            Integer addingWay = record.getAddingWay();
            List<Student> studentList = new ArrayList<>();
            if (addingWay == 1) {
                String userIds = record.getStudentUserIds();
                String[] split = userIds.split(",");
                for (String s : split) {
                    Student byUserId = studentDao.getByUserId(s);
                    studentList.add(byUserId);
                }
            } else if (addingWay == 2) {
                String groupIds = record.getGroupIds();
                String[] split = groupIds.split(",");
                for (String s : split) {
                    EvaluationGroup byId = groupDao.getById(Long.parseLong(s));
                    String userIds = byId.getStudentUserIds();
                    String[] split1 = userIds.split(",");
                    for (String userId : split1) {
                        Student byUserId = studentDao.getByUserId(userId);
                        studentList.add(byUserId);
                    }
                }
            }
            record.setStudentList(studentList);

            setRecordConcatName(record);
        }
        return JsonResultUtil.success(new PageEntity<>(evaluationRecordList));
    }

    @Override
    public JsonResult listClassStudentEvaluationScore(Student student) {
        List<EvaluationStudentScore> evaluationStudentScores = studentDao.listClassStudentEvaluationScore(student);
        return JsonResultUtil.success(new PageEntity<>(evaluationStudentScores));
    }

    @Override
    public JsonResult getRecordStatisticsForStudent(String studentUserId) {
        Map<String,Object> map = new HashMap<>();
        List<RecordStatisticsForStudent> dimensionList = recordDao.getRecordStatisticsForStudent(studentUserId);
        BigDecimal totalPraiseScore = new BigDecimal("0");
        BigDecimal totalCriticalScore = new BigDecimal("0");
        for (RecordStatisticsForStudent dimension : dimensionList) {
            BigDecimal praiseItemScore = dimension.getPraiseItemCount();
            BigDecimal criticalItemScore = dimension.getCriticalItemCount();
            totalPraiseScore = totalPraiseScore.add(praiseItemScore);
            totalCriticalScore = totalCriticalScore.add(criticalItemScore);
        }
        map.put("dimensionList",dimensionList);
        map.put("totalCriticalCount",totalCriticalScore);
        map.put("totalPraiseCount",totalPraiseScore);
        return JsonResultUtil.success(map);
    }

    @Override
    public JsonResult getRecordStatisticsForStudentInToday(EvaluationRecord evaluationRecord) {
        Map<String,Object> map = new HashMap();
        List<EvaluationRecord> recordListForStudent = recordDao.getByRecord(evaluationRecord);
        PageEntity<EvaluationRecord> pageEntity = new PageEntity<>(recordListForStudent);
        map.put("pageEntity",pageEntity);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        String s = format.substring(0, 10);
        BigDecimal todayPraiseScore = new BigDecimal("0");
        BigDecimal todayCriticalScore = new BigDecimal("0");
        for (EvaluationRecord r : recordListForStudent) {
            //拼接项目名
            setRecordConcatName(r);
            //指定用户获取今日评价统计数据
            String updateTime = r.getUpdateTime();
            String substring = updateTime.substring(0, 10);
            if(s.equals(substring)){
                BigDecimal score = r.getScore();
                if (score.compareTo(BigDecimal.ZERO) == 1){
                    todayPraiseScore = todayPraiseScore.add(score);
                }else{
                    todayCriticalScore = todayCriticalScore.add(score);
                }
            }
        }
        map.put("todayCriticalCount",todayCriticalScore);
        map.put("todayPraiseCount",todayPraiseScore);
        return JsonResultUtil.success(map);
    }

    public void setRecordConcatName(EvaluationRecord record) {
        Long itemId = record.getItemId();
        EvaluationItem item = itemDao.getById(itemId);
        String itemName = item.getItemName();
        Long dimensionId = item.getDimensionId();
        EvaluationDimension dimension = dimensionDao.getById(dimensionId);
        String dimensionName = dimension.getDimensionName();
        record.setRecordName(dimensionName + "/" + itemName);
    }

}
