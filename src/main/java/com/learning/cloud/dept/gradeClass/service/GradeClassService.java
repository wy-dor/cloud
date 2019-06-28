package com.learning.cloud.dept.gradeClass.service;

import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;

public interface GradeClassService {

    ServiceResult getClassesByTeacher(Teacher teacher);

    ServiceResult getClassesByCampus(Campus campus);

    JsonResult getClassDetails(Integer classId);

    JsonResult getByGradeClass(GradeClass gradeClass);

    JsonResult getGradeClassById(Integer id);
}
