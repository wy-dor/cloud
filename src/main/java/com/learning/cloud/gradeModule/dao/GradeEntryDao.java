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

    List<GradeEntry> getAllGradeEntry(GradeEntry gradeEntry);

    List<GradeEntry> getByClassModule(Long moduleId, Integer classId);

    GradeEntry getByStudentModule(GradeEntry gradeEntry);
}
