package com.learning.cloud.user.student.dao;

import com.learning.cloud.user.student.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StudentDao {
    int insert(Student record);

    Student getByUserId(Student student);

    int update(Student student);

    Integer getClassStuNum(Integer classId);
}
