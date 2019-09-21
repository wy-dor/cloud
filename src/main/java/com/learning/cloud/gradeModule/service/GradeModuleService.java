package com.learning.cloud.gradeModule.service;

import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.domain.JsonResult;

public interface GradeModuleService {

    JsonResult saveGradeModule(GradeModule gradeModule);

    JsonResult getGradeModuleById(Long id);

    JsonResult getAllGradeModule(GradeModule gradeModule);

    JsonResult deleteGradeModule(Long id);
}
