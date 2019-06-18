package com.learning.cloud.course.service.impl;

import com.learning.cloud.course.dao.CourseDetailDao;
import com.learning.cloud.course.dao.CourseExchangeDao;
import com.learning.cloud.course.entity.CourseDetail;
import com.learning.cloud.course.entity.CourseExchange;
import com.learning.cloud.course.service.CourseExchangeService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    /**
     * 课程调换
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
        List<CourseDetail> courseDetails = courseDetailDao.getTeacherCourseDetail(toDetail.getCourseTeacherId(),weekDay);
        //判断当天该老师是否有本节课
        for(CourseDetail bean : courseDetails){
            if(bean.getCourseNum()==courseDetail.getCourseNum()){
                return JsonResultUtil.error(JsonResultEnum.TIME_CONFLICT);
            }
        }
        //可以调课
        int i = courseExchangeDao.addCourseExchange(courseExchange);

        return JsonResultUtil.success(courseExchange.getId());
    }

    /**
     * 获取某日需要调换的课程
     * @param day
     * @return
     * @throws Exception
     */
    @Override
    public JsonResult getCourseExchange(String day) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(day!=null){

        }else {
            day = sdf.format(new Date());
        }
        List<CourseExchange> courseExchanges = courseExchangeDao.getCourseExchange(day);
        return JsonResultUtil.success(courseExchanges);
    }

    @Override
    public JsonResult getMyExchange(Long teacherId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        List<CourseExchange> courseExchanges = courseExchangeDao.getMyExchange(teacherId, day);
        return JsonResultUtil.success(courseExchanges);
    }
}
