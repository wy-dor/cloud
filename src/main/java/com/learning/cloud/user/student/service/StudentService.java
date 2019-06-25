package com.learning.cloud.user.student.service;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;

public interface StudentService {

    ServiceResult getByUserId(String userId);

    JsonResult getClassStudentNum(Integer classId);

    JsonResult getClassStudents(GradeClass gradeClass);
}
