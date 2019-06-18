package com.learning.cloud.student.dao;

import com.learning.cloud.student.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StudentDao {
    int insert(Student record);

}
