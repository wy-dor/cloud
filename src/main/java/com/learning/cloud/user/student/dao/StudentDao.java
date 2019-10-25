package com.learning.cloud.user.student.dao;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.evaluation.entity.EvaluationStudentScore;
import com.learning.cloud.evaluation.entity.StuInfo;
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

    List<String> listStudentUserIdInClass(Integer classId);

    List<Student> getStudentsByName(Student student);

    int delete(Integer id);

    int deleteByUserId(String userId);

    int deleteByClassId(Integer classId);

    StuInfo getStuInfoByUserId(String userId);

    List<StuInfo> listStuInfoInClass(Integer classId);

    List<EvaluationStudentScore> listClassStudentEvaluationScore(Student student);
}
