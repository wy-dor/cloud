package com.learning.cloud.dept.gradeClass.service.impl;

import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.gradeClass.service.GradeClassService;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GradeClassServiceImpl implements GradeClassService {
    @Autowired
    private GradeClassDao gradeClassDao;


    @Override
    public ServiceResult getClassesByTeacher(Teacher teacher) {
        List<GradeClass> classList = new ArrayList<>();
        String classIds = teacher.getClassIds();
        String[] idList = classIds.split(",");
        for (String idStr : idList) {
            GradeClass byId = gradeClassDao.getById(Integer.parseInt(idStr));
            classList.add(byId);
        }
        return ServiceResult.success(classList);
    }
}
