package com.learning.cloud.grade.dao;

import com.learning.cloud.grade.entity.GradeModule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GradeModuleDao {
    int insert(GradeModule record);

    GradeModule getById(Long id);

    List<GradeModule> getAllGradeModule(GradeModule gradeModule);

    int deleteGradeModule(Long id);
}
