package com.learning.cloud.course.service.impl;

import com.github.pagehelper.PageInfo;
import com.learning.cloud.course.dao.CourseDao;
import com.learning.cloud.course.entity.Course;
import com.learning.cloud.course.service.CourseService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-11 16:51
 * @Desc:
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public JsonResult getCourseByClassId(Long classId) throws Exception {
        Course course = courseDao.getCourseByClassId(classId);
        return JsonResultUtil.success(course);
    }

    @Override
    public JsonResult addCourse(Course course) throws Exception {
        int i = courseDao.addCourse(course);
        return JsonResultUtil.success(course.getId());
    }

    @Override
    public JsonResult editCourse(Course course) throws Exception {
        int i = courseDao.editCourse(course);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public JsonResult getSchoolCourse(Course course) throws Exception {
        List<Course> courses = courseDao.getSchoolCourse(course);
        return JsonResultUtil.success(new PageInfo<>(courses));
    }

    @Override
    public JsonResult publishCourse(Long id) throws Exception {
        int i = courseDao.publishCourse(id);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
        }
    }
}
