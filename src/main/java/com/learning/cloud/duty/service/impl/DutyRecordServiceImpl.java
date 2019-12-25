package com.learning.cloud.duty.service.impl;

import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.cloud.duty.dao.DutyRecordDao;
import com.learning.cloud.duty.dao.DutyTypeDao;
import com.learning.cloud.duty.entity.DutyRecord;
import com.learning.cloud.duty.entity.RecordStatistics;
import com.learning.cloud.duty.service.DutyRecordService;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yyt
 * @Date: 2019-09-16 15:41
 * @Desc:
 */
@Service
public class DutyRecordServiceImpl implements DutyRecordService {

    @Autowired
    private DutyRecordDao dutyRecordDao;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private GradeClassDao classDao;

    @Autowired
    private DutyTypeDao typeDao;

    @Override
    public JsonResult setTeachersForDutyCheck(String teacherIds, Integer dutyInspector) {
        String[] split = teacherIds.split(",");
        List<String> teacherIdList = new ArrayList<>();
        LOOP:
        for (String s : split) {
            for (String s1 : teacherIdList) {
                if (s.equals(s1)) {
                    continue LOOP;
                }
            }
            teacherIdList.add(s);
        }
        for (String teacherId : teacherIdList) {
            Teacher t = new Teacher();
            t.setId(Integer.parseInt(teacherId));
            t.setDutyInspector(dutyInspector);
            teacherDao.update(t);
        }
        return JsonResultUtil.success("设置成功");
    }

    @Override
    public JsonResult addDutyRecord(DutyRecord dutyRecord) {
        int i = dutyRecordDao.addDutyRecord(dutyRecord);
        if (i > 0) {
            return JsonResultUtil.success(dutyRecord.getId());
        } else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult addPics(List<MultipartFile> fileList) throws Exception {
        String picIds = "";
        if (fileList != null && fileList.size() > 0) {
            for (MultipartFile multipartFile : fileList) {
                Long picId = questionService.reduceImg(multipartFile);
                if (!picIds.equals("")) {
                    picIds += "," + picId.toString();
                } else {
                    picIds = picId.toString();
                }
            }
        }
        return JsonResultUtil.success(picIds);
    }

    @Override
    public JsonResult deleteDutyRecord(Long id) throws Exception {
        int i = dutyRecordDao.deleteDutyRecord(id);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.DELETE_ERROR);
        }
    }

    @Override
    public JsonResult updateDutyRecord(DutyRecord dutyRecord) throws Exception {
        int i = dutyRecordDao.updateDutyRecord(dutyRecord);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.UPDATENONE);
        }
    }

    @Override
    public JsonResult getDutyRecordByClassId(DutyRecord dutyRecord) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        List<DutyRecord> dutyRecords = dutyRecordDao.getDutyRecordByClassId(dutyRecord);
        //对结果按时间分组，返回数组
        HashSet set = new HashSet();
        for (int i = 0; i < dutyRecords.size(); i++) {
            c1.setTime(formatter.parse(dutyRecords.get(i).getDay()));
            List<DutyRecord> duty = new ArrayList<>();
            for (int j = 0; j < dutyRecords.size(); j++) {
                c2.setTime(formatter.parse(dutyRecords.get(j).getDay()));
                if (c1.getTime().equals(c2.getTime())) {
                    duty.add(dutyRecords.get(j));
                }
            }
            set.add(duty);
        }
        return JsonResultUtil.success(set);
    }

    // 批量新增积分记录
    @Override
    public JsonResult addDutyRecordList(List<DutyRecord> dutyRecordList) throws Exception {
        for (DutyRecord dutyRecord : dutyRecordList) {
            dutyRecordDao.addDutyRecord(dutyRecord);
        }
        return JsonResultUtil.success();
    }

    @Override
    public Map<String, Object> getDutyRecordStatistics(Integer schoolId, String gradeName, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        //获取班级
        GradeClass gc = new GradeClass();
        gc.setSchoolId(schoolId);
        if (!gradeName.equals("")) {
            gc.setGradeName(gradeName);
        }
        List<GradeClass> byGradeClass = classDao.getByGradeClass(gc);
        String classIds = "";
        List<String> classNameList = new ArrayList<>();
        for (GradeClass gradeClass : byGradeClass) {
            Integer classId = gradeClass.getId();
            classNameList.add(gradeClass.getClassName());
            if (!classIds.equals("")) {
                classIds += "," + classId.toString();
            } else {
                classIds = classId.toString();
            }
        }
        map.put("classNameList", classNameList);

        //计算总分
        Integer totalPointInSchool = typeDao.getTotalPointInSchool(schoolId);
        if (totalPointInSchool == null) {
            totalPointInSchool = 0;
        }
        map.put("totalPoint", totalPointInSchool);

        //每个班级每天的加扣分统计
        List<RecordStatistics> statList_0 = new ArrayList<>();
        List<RecordStatistics> statList_1 = dutyRecordDao.getRecordPointStatistics(startTime, endTime, classIds);
        //获取周一到周日的日期"yyyy-MM-dd"
        List<String> weekDays = null;
        try {
            weekDays = getWeekDays(startTime, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (GradeClass gradeClass : byGradeClass) {
            Integer classId = gradeClass.getId();
            String className = gradeClass.getClassName();
            for (String weekDay : weekDays) {
                RecordStatistics rs = new RecordStatistics();
                rs.setClassId(classId);
                rs.setClassName(className);
                rs.setDay(weekDay);
                rs.setPoint(new BigDecimal(totalPointInSchool));
                statList_0.add(rs);
            }
        }
        for (RecordStatistics rs1 : statList_1) {
            Integer classId = rs1.getClassId();
            String day = rs1.getDay();
            for (RecordStatistics rs_0 : statList_0) {
                if (classId == rs_0.getClassId() && day.equals(rs_0.getDay().substring(0, 10))) {
                    BigDecimal add = rs1.getPoint().add(new BigDecimal(totalPointInSchool));
                    rs_0.setPoint(add);
                }
            }
        }
        map.put("rsList", statList_0);
        return map;
    }

    /**
     * 获得某个日期周一到周五的日期列表
     *
     * @param date       待查询的日期字符串
     * @param dateFormat 日期格式
     * @return 周一到周五的日期字符串列表
     * @throws ParseException
     */
    public List<String> getWeekDays(String date, String dateFormat) throws ParseException {
        List<String> list = new ArrayList<String>();
        Calendar c = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String[] weekDays = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        c.setTime(sdf.parse(date));
        int currentYear = c.get(Calendar.YEAR);
        int weekIndex = c.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        //一周的开始为周日
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去
        if (dayOfWeek == 1) {
            c.add(Calendar.DAY_OF_MONTH, -1);
            String date_str = sdf.format(c.getTime());
            list = getWeekDays(date_str, dateFormat);
        } else {
            c.setWeekDate(currentYear, weekIndex, 1);
            for (int i = 1; i <= 7; i++) {
                c.add(Calendar.DATE, 1);
                String date_str = sdf.format(c.getTime());
                list.add(date_str + "/" + weekDays[i - 1]);
            }
        }
        return list;
    }
}
