package com.learning.cloud.user.teacher.service.impl;

import com.learning.cloud.user.teacher.service.TeacherService;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
