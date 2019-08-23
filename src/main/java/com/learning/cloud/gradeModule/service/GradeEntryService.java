package com.learning.cloud.gradeModule.service;

import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.domain.JsonResult;

public interface GradeEntryService {
    JsonResult addGradeEntry(GradeEntry gradeEntry);

    JsonResult getGradeEntryById(Long id);

    JsonResult getAllGradeEntry(GradeEntry gradeEntry);
}
