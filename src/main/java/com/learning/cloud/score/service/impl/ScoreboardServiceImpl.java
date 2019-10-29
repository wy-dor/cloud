package com.learning.cloud.score.service.impl;

import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.score.dao.ScoreRecordDao;
import com.learning.cloud.score.dao.ScoreboardDao;
import com.learning.cloud.score.entity.*;
import com.learning.cloud.score.service.ScoreboardService;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
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
    private SchoolDao schoolDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private GradeClassDao gradeClassDao;

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
        School school = schoolDao.getBySchoolId(schoolId.intValue());
        double teacherAvg = 0.0;
        double parentAvg = 0.0;
        if(schoolId!=null){
            //获取所有老师
            List<Teacher> teachers = teacherDao.getTeacherIds(schoolId);
            //取老师最新的积分
            Long sum_teacher_score = new Long(0);
            int ts = teachers.size();
            if(ts>0){
                for(Teacher te: teachers){
                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
                    Long score = new Long(0);
                    if(last!=null){
                        score = last.getScore()==null?0:last.getScore();
                    }
                    sum_teacher_score += score;
                }
                teacherAvg = sum_teacher_score/ts;
            }else{
                teacherAvg = 0.0;
            }
            //取家长最新积分
            List<Parent> parents = parentDao.getParents(schoolId);
            Long sum_parent_score = new Long(0);
            int ps = parents.size();
            if(ps>0){
                for(Parent pa : parents){
                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(pa.getUserId());
                    Long score = new Long(0);
                    if(last!=null){
                        score = last.getScore()==null?0:last.getScore();
                    }
                    if(score!=0){
                        sum_parent_score += score;
                    }else {
                        ps--;
                    }
                }
                if(ps>0){
                    parentAvg = sum_parent_score/ps;
                }
            }else {
                parentAvg = 0.0;
            }

            //计算最终的积分
            double score = teacherAvg*0.8 + parentAvg*0.2;
            //保存到数据库表中
            SchoolScoreboard schoolScoreboard = new SchoolScoreboard();
            schoolScoreboard.setSchoolId(schoolId);
            schoolScoreboard.setScore(new Double(score).intValue());
            schoolScoreboard.setBureauId(school.getBureauId().longValue());
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
        GradeClass gradeClass = gradeClassDao.getById(classId.intValue());
        if(classId!=null){
            double teacherAvg = 0.0;
            double parentAvg = 0.0;
            //获取所有老师
            List<Teacher> teachers = teacherDao.getClassTeachers(gradeClass);
            //取老师最新的积分
            Long sum_teacher_score = new Long(0);
            int ts = teachers.size();
            if(ts>0){
                for(Teacher te: teachers){
                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
                    Long score = new Long(0);
                    if(last!=null){
                        score = last.getScore()==null?0:last.getScore();
                    }
                    sum_teacher_score += score;
                }
                teacherAvg = sum_teacher_score/ts;
            }else{
                teacherAvg = 0.0;
            }
            //取家长最新积分
            List<Parent> parents = parentDao.getParents(classId);
            Long sum_parent_score = new Long(0);
            int ps = parents.size();
            if(ps>0){
                for(Parent pa : parents){
                    ScoreRecord last = scoreRecordDao.getLastScoreRecord(pa.getUserId());
                    Long score = new Long(0);
                    if(last!=null){
                        score = last.getScore()==null?0:last.getScore();
                    }
                    if(score!=0){
                        sum_parent_score += score;
                    }else {
                        ps--;
                    }
                }
                if(ps>0){
                    parentAvg = sum_parent_score/ps;
                }
            }else {
                parentAvg = 0.0;
            }
            //计算最终的积分
            double score = teacherAvg*0.8 + parentAvg*0.2;
            //保存到数据库表中
            ClassScoreboard classScoreboard = new ClassScoreboard();
            classScoreboard.setClassId(classId);
            classScoreboard.setScore(new Double(score).intValue());
            classScoreboard.setSchoolId(gradeClass.getSchoolId().longValue());
            classScoreboard.setBureauId(gradeClass.getBureauId().longValue());
            int i = scoreboardDao.addClassScoreboard(classScoreboard);
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.SCORE_SCHOOL_ID_NEED);
        }
    }


    @Override
    public JsonResult getTeacherRank(Long bureauId) throws Exception{
        List<ScoreRank> scoreRanks = scoreboardDao.getTeacherRank(bureauId);
        return JsonResultUtil.success(new PageEntity<>(scoreRanks));
    }

    @Override
    public JsonResult getClassRank(Long schoolId) throws Exception {
        List<ClassScoreboard> classScoreboards = scoreboardDao.getClassRank(schoolId);
        return JsonResultUtil.success(classScoreboards);
    }

    // 新积分规则
    @Override
    public JsonResult getBureauPersonnelRank(ScoreRank scoreRank) throws Exception {
        List<ScoreRank> scoreRanks = scoreboardDao.getBureauPersonnelRank(scoreRank);
        return JsonResultUtil.success(new PageEntity<>(scoreRanks));
    }

    @Override
    public JsonResult getSchoolRank(ScoreRank scoreRank) throws Exception {
        List<ScoreRank> scoreRanks = scoreboardDao.getSchoolRank(scoreRank);
        return JsonResultUtil.success(new PageEntity<>(scoreRanks));
    }

    @Override
    public JsonResult getPersonnelRank(ScoreRank scoreRank) throws Exception {
        List<ScoreRank> scoreRanks = scoreboardDao.getPersonnelRank(scoreRank);
        return JsonResultUtil.success(new PageEntity<>(scoreRanks));
    }

    @Override
    public JsonResult getClassRankFromDuty(ScoreRank scoreRank) throws Exception {
        List<ScoreRank> scoreRanks = scoreboardDao.getClassRankFromDuty(scoreRank);
        return JsonResultUtil.success(new PageEntity<>(scoreRanks));
    }
}
