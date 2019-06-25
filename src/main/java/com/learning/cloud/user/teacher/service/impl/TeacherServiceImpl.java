package com.learning.cloud.user.teacher.service.impl;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.user.teacher.service.TeacherService;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;


    @Override
    public ServiceResult getByUserId(String userId) {
        Teacher byUserId = teacherDao.getByUserId(userId);
        return ServiceResult.success(byUserId);
    }

    @Override
    public JsonResult getClassTeacherNum(Integer classId) {
        Integer teacherNum = teacherDao.getClassTeacherNum(classId);
        return JsonResultUtil.success(teacherNum);
    }

    @Override
    public JsonResult getClassTeachers(GradeClass gradeClass) {
        List<Teacher> teacherList = teacherDao.getClassTeachers(gradeClass);
        return JsonResultUtil.success(new PageEntity<>(teacherList));
    }
}
