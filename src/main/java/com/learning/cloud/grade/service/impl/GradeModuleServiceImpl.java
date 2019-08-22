package com.learning.cloud.grade.service.impl;

import com.learning.cloud.grade.dao.GradeModuleDao;
import com.learning.cloud.grade.entity.GradeModule;
import com.learning.cloud.grade.service.GradeModuleService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GradeModuleServiceImpl implements GradeModuleService {

    @Autowired
    private GradeModuleDao gradeModuleDao;

    @Override
    public JsonResult addGradeModule(GradeModule gradeModule) {
        gradeModuleDao.insert(gradeModule);
        return JsonResultUtil.success(gradeModule.getId());
    }

    @Override
    public JsonResult getGradeModuleById(Long id) {
        GradeModule gradeModule = gradeModuleDao.getById(id);
        return JsonResultUtil.success(gradeModule);
    }

    @Override
    public JsonResult getAllGradeModule(GradeModule gradeModule) {
        List<GradeModule> gradeModuleList = gradeModuleDao.getAllGradeModule(gradeModule);
        return JsonResultUtil.success(new PageEntity<>(gradeModuleList));
    }

    @Override
    public JsonResult deleteGradeModule(Long id) {
        gradeModuleDao.deleteGradeModule(id);
        return JsonResultUtil.success();
    }
}
