package com.learning.cloud.user.teacher.service;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;

public interface TeacherService {

    ServiceResult getByUserId(String userId);

    JsonResult getClassTeacherNum(Integer classId);

    JsonResult getClassTeachers(GradeClass gradeClass);
}
