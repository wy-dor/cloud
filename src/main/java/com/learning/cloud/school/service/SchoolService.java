package com.learning.cloud.school.service;

import com.learning.cloud.school.entity.School;
import com.learning.domain.JsonResult;

public interface SchoolService {
    Integer setBureauId(School school);

    JsonResult getBySchool(School school);

    JsonResult getSchoolIdByCorpId(String corpId);
}
