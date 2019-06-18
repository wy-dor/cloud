package com.learning.cloud.school.service.impl;

import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.school.service.SchoolService;
import com.learning.cloud.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    public SchoolDao schoolDao;

    @Override
    public ServiceResult setBureauId(School school) {
        School s = schoolDao.getBySchoolName(school.getSchoolName());
        if(s == null){
            return ServiceResult.success("不存在该学校");
        }
        int i = schoolDao.update(school);
        return ServiceResult.success("设置成功");
    }
}
