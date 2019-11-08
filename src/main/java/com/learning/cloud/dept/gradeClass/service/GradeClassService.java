package com.learning.cloud.dept.gradeClass.service;

import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;

public interface GradeClassService {

    JsonResult getClassesByTeacher(Integer teacherId);

    ServiceResult getClassesByCampus(Campus campus);

    JsonResult getClassDetails(Integer classId);

    JsonResult getByGradeClass(GradeClass gradeClass);

    JsonResult getGradeClassById(Integer id);

    JsonResult getAllGradeName(Integer schoolId, String userId);

    JsonResult listGradeClassByTeacherInSchool(String userId, Integer schoolId);

    Integer getCampusIdForTeacher(String userId, Integer schoolId);

    JsonResult getAllGradeNameByCampusId(Integer campusId);

    JsonResult listGradeClassByIds(String classIds);
}
