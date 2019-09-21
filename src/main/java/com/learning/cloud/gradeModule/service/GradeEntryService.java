package com.learning.cloud.gradeModule.service;

import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeEntryJsonStr;
import com.learning.domain.JsonResult;

public interface GradeEntryService {
    JsonResult saveGradeEntry(GradeEntryJsonStr gradeEntryJsonStr);

    JsonResult getGradeEntryById(Long id);

    JsonResult getByGradeEntry(GradeEntry gradeEntry);

    void saveEntryMarks(GradeEntry gradeEntry, Integer studentId, String marks);
}
