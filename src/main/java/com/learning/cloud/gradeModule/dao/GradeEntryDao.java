package com.learning.cloud.gradeModule.dao;

import com.learning.cloud.gradeModule.entity.GradeEntry;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GradeEntryDao {
    int insert(GradeEntry record);

    GradeEntry getById(Long id);

    List<GradeEntry> getByGradeEntry(GradeEntry gradeEntry);

    int update(GradeEntry gradeEntry);

    List<Integer> getClassIdsInModule(Long moduleId);
}
