package com.learning.cloud.grade.dao;

import com.learning.cloud.grade.entity.GradeModule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GradeModuleDao {
    int insert(GradeModule record);

    GradeModule getById(Integer id);
}
