package com.learning.cloud.dept.gradeClass.dao;

import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.school.entity.School;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GradeClassDao {

    int insert(GradeClass gradeClass);

    List<GradeClass> getByGradeClass(GradeClass gradeClass);

    GradeClass getById(Integer id);

    List<GradeClass> getClassesByCampusId(Campus campus);

    int update(GradeClass gradeClass);
}
