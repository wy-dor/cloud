package com.learning.cloud.grade.service;

import com.learning.cloud.grade.entity.GradeModule;
import com.learning.domain.JsonResult;

public interface GradeModuleService {

    JsonResult addGradeModule(GradeModule gradeModule);

    JsonResult getGradeModuleById(Long id);

    JsonResult getAllGradeModule(GradeModule gradeModule);

    JsonResult deleteGradeModule(Long id);
}
