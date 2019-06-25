package com.learning.cloud.dept.campus.service.impl;

import com.learning.cloud.dept.campus.dao.CampusDao;
import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.campus.service.CampusService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CampusServiceImpl implements CampusService {
    @Autowired
    private CampusDao campusDao;

    @Override
    public JsonResult getSchoolCampuses(Integer schoolId) {
        List<Campus> campuses = campusDao.getSchoolCampuses(schoolId);
        return JsonResultUtil.success(new PageEntity<>(campuses));
    }
}
