package com.learning.cloud.user.teacher.dao;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.user.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeacherDao {

    List<Teacher> getClassTeachers(GradeClass gradeClass);

    Teacher getById(Integer id);

    int insert(Teacher record);

    Teacher getByUserId(String userId);

    int update(Teacher teacher);

    Integer getClassTeacherNum(Integer classId);
}
