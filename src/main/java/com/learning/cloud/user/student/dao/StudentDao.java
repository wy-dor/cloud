package com.learning.cloud.user.student.dao;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.user.student.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentDao {
    int insert(Student record);

    Student getByUserId(String userId);

    int update(Student student);

    Integer getClassStuNum(Integer classId);

    List<Student> getClassStudents(GradeClass gradeClass);

    List<Integer> getStudentIdListInClass(Integer classId);

    List<Student> getStudentsByName(Student student);

    int delete(Integer id);

    int deleteByClassId(Integer classId);
}
