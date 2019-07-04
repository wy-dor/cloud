package com.learning.cloud.course.service.impl;

import com.learning.cloud.course.dao.CourseDetailDao;
import com.learning.cloud.course.entity.CourseDetail;
import com.learning.cloud.course.service.CourseDetailService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-12 19:23
 * @Desc:
 */
@Service
public class CourseDetailServiceImpl implements CourseDetailService {

    @Autowired
    private CourseDetailDao courseDetailDao;

    @Override
    public JsonResult getCourseDetailByClassId(Long classId, Integer weekDay) throws Exception {
        List<CourseDetail> courseDetails = courseDetailDao.getCourseDetailByClassId(classId, weekDay);
        return JsonResultUtil.success(courseDetails);
    }

    @Override
    public JsonResult addCourseDetail(CourseDetail courseDetail) throws Exception {
        int i = courseDetailDao.addCourseDetail(courseDetail);
        return JsonResultUtil.success(courseDetail.getId());
    }

    @Override
    public JsonResult editCourseDetail(CourseDetail courseDetail) throws Exception {
        int i  = courseDetailDao.editCourseDetail(courseDetail);
        return null;
    }

    @Override
    public JsonResult getTeacherCourseDetail(Long teacherId, Integer weekDay) throws Exception {
        List<CourseDetail> courseDetails = courseDetailDao.getTeacherCourseDetail(teacherId, weekDay);
        return JsonResultUtil.success(courseDetails);
    }

    @Override
    public JsonResult getCourseDetailById(Long id) throws Exception {
        CourseDetail courseDetail = courseDetailDao.getCourseDetailById(id);
        return JsonResultUtil.success(courseDetail);
    }

    @Override
    public JsonResult deleteCourseDetailById(Long id) throws Exception {
        int i = courseDetailDao.deleteCourseDetailById(id);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.DELETE_ERROR);
        }
    }

    @Override
    public JsonResult deleteAllCourseDetail(Long courseId) throws Exception {
        int i = courseDetailDao.deleteAllCourseDetail(courseId);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.DELETE_ERROR);
        }
    }
}
