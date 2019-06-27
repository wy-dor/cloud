package com.learning.cloud.school.service.impl;

import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.school.service.SchoolService;
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
}
