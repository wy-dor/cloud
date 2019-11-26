package com.learning.cloud.course.service.impl;

import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.learning.cloud.course.dao.CourseDetailDao;
import com.learning.cloud.course.dao.CourseExchangeDao;
import com.learning.cloud.course.entity.CourseDetail;
import com.learning.cloud.course.entity.CourseExchange;
import com.learning.cloud.course.service.CourseExchangeService;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.workProcess.dao.ProcessInstanceDao;
import com.learning.cloud.workProcess.entity.ProcessInstance;
import com.learning.cloud.workProcess.service.ProcessInstanceService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.learning.cloud.util.Utils.getWeekDay;

/**
 * @Author: yyt
 * @Date: 2019-06-13 16:10
 * @Desc:
 */
@Service
public class CourseExchangeServiceImpl implements CourseExchangeService {

    @Autowired
    private CourseExchangeDao courseExchangeDao;

    @Autowired
    private CourseDetailDao courseDetailDao;

    @Autowired
    private ProcessInstanceService processInstanceService;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private ProcessInstanceDao processInstanceDao;

    /**
     * 课程调换
     *
     * @param courseExchange
     * @return
     * @throws Exception
     */
    @Override
    public JsonResult addCourseExchange(CourseExchange courseExchange) throws Exception {
        //1.只能调换未发生的时间的课程
        //2.只能调换每周都上的课程
        //3.单双周暂不支持
        //4.调换其他老师的课程，先判断该被调课老师在当前时间是否有其它课
        Date fromDay = courseExchange.getFromDay();
        Integer weekDay = getWeekDay(fromDay);
        CourseDetail courseDetail = courseDetailDao.getCourseDetailById(courseExchange.getFromId());
        CourseDetail toDetail = courseDetailDao.getCourseDetailById(courseExchange.getToId());
        List<CourseDetail> courseDetails = courseDetailDao.getTeacherCourseDetail(toDetail.getCourseTeacherId(), weekDay);
        //判断当天该老师是否有本节课
        for (CourseDetail bean : courseDetails) {
            if (bean.getCourseNum() == courseDetail.getCourseNum()) {
                return JsonResultUtil.error(JsonResultEnum.TIME_CONFLICT);
            }
        }
        //可以调课
        int i = courseExchangeDao.addCourseExchange(courseExchange);

        return JsonResultUtil.success(courseExchange.getId());
    }

    /**
     * 获取某日需要调换的课程
     *
     * @param classId
     * @param day
     * @return
     * @throws Exception
     */
    @Override
    public JsonResult getCourseExchange(Long classId, String day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (day != null) {

        } else {
            day = sdf.format(new Date());
        }
        List<CourseExchange> courseExchanges = courseExchangeDao.getCourseExchange(classId, day);
        return JsonResultUtil.success(courseExchanges);
    }

    @Override
    public JsonResult getMyExchange(Long teacherId, Integer status) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        List<CourseExchange> courseExchanges = courseExchangeDao.getMyExchange(teacherId, day, status);
        return JsonResultUtil.success(courseExchanges);
    }

    @Override
    public JsonResult confirmExchange(CourseExchange courseExchange) throws Exception {
        int i = courseExchangeDao.confirmExchange(courseExchange);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void renewCourseExchangeStatus(CourseExchange courseExchange) throws Exception {
        List<CourseExchange> courseExchangeList = courseExchangeDao.listCourseExchangeForRenew(courseExchange);
        for (CourseExchange exchange : courseExchangeList) {
            String processInstanceId = exchange.getProcessInstanceId();
            if (!processInstanceId.equals("")) {
                Long classId = exchange.getClassId();
                GradeClass gradeClass = gradeClassDao.getById(classId.intValue());
                Integer schoolId = gradeClass.getSchoolId();
                School school = schoolDao.getBySchoolId(schoolId);
                String corpId = school.getCorpId();
                OapiProcessinstanceGetResponse response = processInstanceService.getInstanceStatus(processInstanceId, corpId);
                OapiProcessinstanceGetResponse.ProcessInstanceTopVo responseProcessInstance = response.getProcessInstance();
                String status = responseProcessInstance.getStatus();
                String result = responseProcessInstance.getResult();
                if (status.equals("COMPLETED") || status.equals("TERMINATED")) {
                    Integer exchangeStatus = 2;
                    if(status.equals("TERMINATED")){
                        exchangeStatus = 0;
                    }else {
                        if(result.equals("refuse")){
                            exchangeStatus = 0;
                        }
                    }
                    exchange.setStatus(exchangeStatus.shortValue());
                    courseExchangeDao.confirmExchange(exchange);
                    //审批实例表更新状态
                    ProcessInstance instance = new ProcessInstance();
                    instance.setProcessInstanceId(processInstanceId);
                    instance.setStatus((short)2);
                    processInstanceDao.update(instance);
                }
            }
        }
    }
}
