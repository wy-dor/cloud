package com.learning.cloud.dept.gradeClass.service;

import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;

public interface GradeClassService {

    ServiceResult getClassesByTeacher(Teacher teacher);
}
