package com.learning.cloud.grade.service.impl;

import com.learning.cloud.grade.dao.GradeModuleDao;
import com.learning.cloud.grade.entity.GradeModule;
import com.learning.cloud.grade.service.GradeModuleService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GradeModuleServiceImpl implements GradeModuleService {

    @Autowired
    private GradeModuleDao gradeModuleDao;

    @Override
    public JsonResult addGradeModule(GradeModule gradeModule) {
        gradeModuleDao.insert(gradeModule);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult getById(Integer id) {
        GradeModule gradeModule = gradeModuleDao.getById(id);
        return JsonResultUtil.success(gradeModule);
    }
}
