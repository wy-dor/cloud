package com.learning.cloud.dept.topApi.dao;

import com.learning.cloud.dept.topApi.entity.EduGrade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EduGradeDao {
    List<EduGrade> getByGrade(EduGrade eduGrade);

    int insert(EduGrade eduGrade);

    int update(EduGrade eduGrade);
}
