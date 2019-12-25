package com.learning.cloud.user.student.service.impl;

import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.cloud.user.student.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private GradeClassDao gradeClassDao;


    @Override
    public ServiceResult getByUserId(String userId) {
        Student byUserId = studentDao.getByUserId(userId);
        return ServiceResult.success(byUserId);
    }

    @Override
    public JsonResult getClassStudentNum(Integer classId) {
        Integer studentNum = studentDao.getClassStuNum(classId);
        return JsonResultUtil.success(studentNum);
    }

    @Override
    public JsonResult getClassStudents(GradeClass gradeClass) {
        Integer id = gradeClass.getId();
        if (id == null) {
            List<GradeClass> byGradeClass = gradeClassDao.getByGradeClass(gradeClass);
            if (byGradeClass != null && byGradeClass.size() > 0) {
                id = byGradeClass.get(0).getId();
                gradeClass.setId(id);
            }
        }
        List<Student> studentList = studentDao.getClassStudents(gradeClass);
        return JsonResultUtil.success(new PageEntity<>(studentList));
    }

    @Override
    public JsonResult getStudentsByName(Student student) {
        List<Student> studentList = studentDao.getStudentsByName(student);
        return JsonResultUtil.success(new PageEntity<>(studentList));
    }
}
