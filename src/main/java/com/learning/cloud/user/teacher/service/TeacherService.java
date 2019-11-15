package com.learning.cloud.user.teacher.service;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;

public interface TeacherService {

    JsonResult listTeacher(Teacher teacher);

    ServiceResult getByUserId(String userId);

    JsonResult getClassTeacherNum(Integer classId);

    JsonResult getClassTeachers(GradeClass gradeClass);

    JsonResult setTeacherCourseType(Teacher teacher);

    JsonResult removeTeacherFromClass(Integer teacherId, Integer classId);

    JsonResult getTeachersInSchool(Integer schoolId);

    JsonResult getTeacherById(Integer teacherId)throws Exception;

    int removeTeachersInClass(Integer cId);

    int removeTeacherInClass(Integer classId, Teacher teacher);

    JsonResult getTeacherByUserIdAndSchoolId(Teacher teacher);
}
