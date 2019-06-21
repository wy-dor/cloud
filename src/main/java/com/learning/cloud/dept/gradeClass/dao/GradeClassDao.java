package com.learning.cloud.dept.gradeClass.dao;

import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GradeClassDao {

    int insert(GradeClass gradeClass);

    GradeClass getByGradeClass(GradeClass gradeClass);

    GradeClass getById(Integer id);

}
