package com.learning.cloud.teacher.dao;

import com.learning.cloud.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TeacherDao {
    int insert(Teacher record);

}
