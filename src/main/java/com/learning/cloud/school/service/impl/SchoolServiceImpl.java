package com.learning.cloud.school.service.impl;

import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.school.service.SchoolService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public Integer setBureauId(School school) {
        int i = schoolDao.update(school);
        return i;
    }

    @Override
    public List<School> getBySchool(School school) {
        return schoolDao.getBySchool(school);
    }

    @Override
    public JsonResult getSchoolIdByCorpId(String corpId) {
        Integer schoolId = null;
        School school = schoolDao.getSchoolByCorpId(corpId);
        if(school != null){
            schoolId = school.getId();
        }else{
            schoolId = -1;
        }
        return JsonResultUtil.success(schoolId);
    }
}
