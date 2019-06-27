package com.learning.cloud.score.service.impl;

import com.learning.cloud.score.dao.ScoreRecordDao;
import com.learning.cloud.score.dao.ScoreboardDao;
import com.learning.cloud.score.entity.ClassScoreboard;
import com.learning.cloud.score.entity.SchoolScoreboard;
import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.cloud.score.entity.TeacherScoreboard;
import com.learning.cloud.score.service.ScoreboardService;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreboardServiceImpl implements ScoreboardService {

    @Autowired
    private ScoreboardDao scoreboardDao;

    @Autowired
    private ScoreRecordDao scoreRecordDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private ParentDao parentDao;
    /**
     * 获取学校的积分值，取老师的平均值*系数和家长的平均值*系数
     * @param schoolId
     * @return
     * @throws Exception
     */
    @Override
    public JsonResult getSchoolScore(Long schoolId) throws Exception {
        SchoolScoreboard schoolScoreboard = scoreboardDao.getSchoolScore(schoolId);
        return JsonResultUtil.success(schoolScoreboard);
    }

    /**
     * 更新学校的积分
     * 每天6点刷新最新的积分，
     * 手动刷新时只能触发一个学校的积分刷新
     * 老师积分占0.8，家长积分占0.2
     * @param schoolId
     * @return
     * @throws Exception
     */
    @Override
    public JsonResult updateSchoolScore(Long schoolId) throws Exception {
        if(schoolId!=null){
            //获取所有老师
            List<Teacher> teachers = teacherDao.getTeacherIds(schoolId);
            //取老师最新的积分
            Long sum_teacher_score = new Long(0);
            int ts = teachers.size();
            for(Teacher te: teachers){
                ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
                sum_teacher_score += last.getScore();
            }
            //取家长最新积分
            List<Parent> parents = parentDao.getParents(schoolId);
            Long sum_parent_score = new Long(0);
            int ps = parents.size();
            for(Parent pa : parents){
                ScoreRecord last = scoreRecordDao.getLastScoreRecord(pa.getUserId());
                Long score = last.getScore();
                if(score!=0){
                    sum_parent_score += score;
                }else {
                    ps--;
                }
            }
            //计算最终的积分
            double score = sum_teacher_score/ts*0.8 + sum_parent_score/ps*0.2;
            //保存到数据库表中
            SchoolScoreboard schoolScoreboard = new SchoolScoreboard();
            schoolScoreboard.setSchoolId(schoolId);
            schoolScoreboard.setScore(new Double(score).intValue());
//            schoolScoreboard.setBureauId(null);
            int i = scoreboardDao.addSchoolScoreboard(schoolScoreboard);
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.SCORE_SCHOOL_ID_NEED);
        }
    }

    /**
     * 获取班级的积分
     * @param classId
     * @return
     * @throws Exception
     */
    @Override
    public JsonResult getClassScore(Long classId) throws Exception {
        ClassScoreboard classScoreboard = scoreboardDao.getClassScore(classId);
        return JsonResultUtil.success(classScoreboard);
    }

    @Override
    public JsonResult updateClassScore(Long classId) throws Exception {
        if(classId!=null){
            //获取所有老师
            List<Teacher> teachers = teacherDao.getTeacherIds(classId);
            //取老师最新的积分
            Long sum_teacher_score = new Long(0);
            int ts = teachers.size();
            for(Teacher te: teachers){
                ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
                sum_teacher_score += last.getScore();
            }
            //取家长最新积分
            List<Parent> parents = parentDao.getParents(classId);
            Long sum_parent_score = new Long(0);
            int ps = parents.size();
            for(Parent pa : parents){
                ScoreRecord last = scoreRecordDao.getLastScoreRecord(pa.getUserId());
                Long score = last.getScore();
                if(score!=0){
                    sum_parent_score += score;
                }else {
                    ps--;
                }
            }
            //计算最终的积分
            double score = sum_teacher_score/ts*0.8 + sum_parent_score/ps*0.2;
            //保存到数据库表中
            ClassScoreboard classScoreboard = new ClassScoreboard();
            classScoreboard.setClassId(classId);
//            classScoreboard.setSchoolId(schoolId);
            classScoreboard.setScore(new Double(score).intValue());
//            classScoreboard.setBureauId(null);
            int i = scoreboardDao.addClassScoreboard(classScoreboard);
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.SCORE_SCHOOL_ID_NEED);
        }
    }


    @Override
    public JsonResult getSchoolRank(Long bureauId) throws Exception {
        List<SchoolScoreboard> schoolScoreboards = scoreboardDao.getSchoolRank(bureauId);
        return JsonResultUtil.success(schoolScoreboards);
    }

    @Override
    public JsonResult getTeacherRank(Long bureauId, Long schoolId) throws Exception{
        List<ScoreRecord> scoreRecords = scoreboardDao.getTeacherRank(bureauId, schoolId);
        return JsonResultUtil.success(scoreRecords);
    }

    @Override
    public JsonResult getClassRank(Long schoolId) throws Exception {
        List<ClassScoreboard> classScoreboards = scoreboardDao.getClassRank(schoolId);
        return null;
    }
}
