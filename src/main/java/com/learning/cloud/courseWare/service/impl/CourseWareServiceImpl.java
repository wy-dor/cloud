package com.learning.cloud.courseWare.service.impl;

import com.learning.cloud.courseWare.dao.CourseWareDao;
import com.learning.cloud.courseWare.entity.CourseWare;
import com.learning.cloud.courseWare.service.CourseWareService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseWareServiceImpl implements CourseWareService {

    @Autowired
    private CourseWareDao courseWareDao;

    //新增课件
    @Override
    public JsonResult addCourseWare(CourseWare courseWare) throws Exception {
        int i = courseWareDao.addCourseWare(courseWare);
        return null;
    }
}
