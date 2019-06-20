package com.learning.cloud.user.teacher.dao;

import com.learning.cloud.user.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TeacherDao {
    int insert(Teacher record);

    Teacher getByUserId(String userId);

    int update(Teacher teacher);
}
