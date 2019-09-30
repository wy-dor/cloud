package com.learning.cloud.gradeModule.dao;

import com.learning.cloud.gradeModule.entity.GradeEntry;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface GradeEntryDao {
    int insert(GradeEntry record);

    GradeEntry getById(Long id);

    List<GradeEntry> getByGradeEntry(GradeEntry gradeEntry);

    int update(GradeEntry gradeEntry);

    List<GradeEntry> getDoneClassSubjectInModule(Long moduleId);

    int deleteByModuleId(Long moduleId);
}
