package com.learning.cloud.course.service.impl;

import com.learning.cloud.course.dao.CourseTypeDao;
import com.learning.cloud.course.entity.CourseType;
import com.learning.cloud.course.service.CourseTypeService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-10 16:47
 * @Desc: 学校课程业务实现类
 */
@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    private CourseTypeDao courseTypeDao;

    @Override
    public JsonResult getSchoolCourseType(Long schoolId) throws Exception {
        List<CourseType> courseTypes = courseTypeDao.getSchoolCourseType(schoolId);
        return JsonResultUtil.success(courseTypes);
    }

    @Override
    public JsonResult addSchoolCourseType(CourseType courseType) throws Exception {
        int i = courseTypeDao.addSchoolCourseType(courseType);
        return JsonResultUtil.success(courseType.getId());
    }

    @Override
    public JsonResult deleteCourseType(Long id) throws Exception {
        int i = courseTypeDao.deleteCourseType(id);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.DELETE_ERROR);
        }
    }
}
