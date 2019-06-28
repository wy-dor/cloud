package com.learning.cloud.school.service;

import com.learning.cloud.school.entity.School;
import com.learning.domain.JsonResult;

import java.util.List;

public interface SchoolService {
    Integer setBureauId(School school);

    List<School> getBySchool(School school);

    JsonResult getSchoolIdByCorpId(String corpId);
}
