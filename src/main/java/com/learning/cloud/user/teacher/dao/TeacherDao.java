package com.learning.cloud.user.teacher.dao;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.user.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeacherDao {
    List<Teacher> getTeachers(Teacher teacher);

    int insert(Teacher record);

    List<Teacher> listTeacherInClass(String classId);

    Teacher getByUserId(String userId);

    Teacher getById(Integer teacherId);

    Teacher getTeacherInSchool(Teacher teacher);

    int update(Teacher teacher);

    Integer getClassTeacherNum(Integer classId);

    List<Teacher> getClassTeachers(GradeClass gradeClass);

    List<String> listTeacherUserIdInClass(Integer classId);

    //获取学校的老师
    List<Teacher> getTeacherIds(Long classId);

    int delete(Integer id);

    List<Teacher> ListTeacherInSchool(Integer schoolId);

    List<Teacher> getClassTeachersWithCourseType(GradeClass gradeClass);
}
