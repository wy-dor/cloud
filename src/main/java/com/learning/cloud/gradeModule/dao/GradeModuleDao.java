package com.learning.cloud.gradeModule.dao;

import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeModule;
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

    int update(GradeModule gradeModule);

    List<GradeModule> getGradeModulesForClass(GradeEntry gradeEntry);
}
