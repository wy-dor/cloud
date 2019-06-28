package com.learning.schedule;

import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.score.dao.ScoreRecordDao;
import com.learning.cloud.score.dao.ScoreboardDao;
import com.learning.cloud.score.entity.ClassScoreboard;
import com.learning.cloud.score.entity.SchoolScoreboard;
import com.learning.cloud.score.entity.ScoreRecord;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class MultiThreadScheduleTask {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private ScoreRecordDao scoreRecordDao;

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private ScoreboardDao scoreboardDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Async
    @Scheduled(cron = "0 0 5 * * ?") //每天5点
    public void refreshSchoolScore() throws InterruptedException {
        //刷新学校的积分
        //1。获取是所有学校
        List<School> schools = schoolDao.getSchools();
        for(School school : schools){
            //获取所有老师
            List<Teacher> teachers = teacherDao.getTeacherIds(school.getId().longValue());
            //取老师最新的积分
            Long sum_teacher_score = new Long(0);
            int ts = teachers.size();
            for(Teacher te: teachers){
                ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
                sum_teacher_score += last.getScore();
            }
            //取家长最新积分
            List<Parent> parents = parentDao.getParents(school.getId().longValue());
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
            schoolScoreboard.setSchoolId(school.getId().longValue());
            schoolScoreboard.setScore(new Double(score).intValue());
            schoolScoreboard.setBureauId(school.getBureauId().longValue());
            int i = scoreboardDao.addSchoolScoreboard(schoolScoreboard);
        }
    }


    @Async
    @Scheduled(cron = "0 0 6 * * ?") //每天6点
    public void refreshClassScore() throws InterruptedException {
        //刷新班级的积分
        //1。获取所有班级
        List<GradeClass> gradeClasses = gradeClassDao.getAllClass();
        for(GradeClass gd : gradeClasses){
            List<Teacher> teachers = teacherDao.getTeacherIds(gd.getId().longValue());
            //取老师最新的积分
            Long sum_teacher_score = new Long(0);
            int ts = teachers.size();
            for(Teacher te: teachers){
                ScoreRecord last = scoreRecordDao.getLastScoreRecord(te.getUserId());
                sum_teacher_score += last.getScore();
            }
            //取家长最新积分
            List<Parent> parents = parentDao.getParents(gd.getId().longValue());
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
            classScoreboard.setClassId(gd.getId().longValue());
            classScoreboard.setScore(new Double(score).intValue());
            classScoreboard.setSchoolId(gd.getSchoolId().longValue());
            classScoreboard.setBureauId(gd.getBureauId().longValue());
            int i = scoreboardDao.addClassScoreboard(classScoreboard);
        }
    }

}
