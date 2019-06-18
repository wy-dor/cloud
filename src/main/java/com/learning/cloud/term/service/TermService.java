package com.learning.cloud.term.service;

import com.learning.cloud.term.entity.Term;
import com.learning.domain.JsonResult;

public interface TermService {
    JsonResult getSchoolTerm(Long schoolId)throws Exception;

    JsonResult editSchoolTerm(Term term)throws Exception;
}
