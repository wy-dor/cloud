package com.learning.cloud.gradeModule.service.impl;

import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.gradeModule.dao.GradeModuleDao;
import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.cloud.gradeModule.service.GradeModuleService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GradeModuleServiceImpl implements GradeModuleService {

    @Autowired
    private GradeModuleDao gradeModuleDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Override
    public JsonResult addGradeModule(GradeModule gradeModule) {
        Integer classesAddingWay = gradeModule.getClassesAddingWay();
        if(classesAddingWay == 2){
            List<Integer> ids = new ArrayList<>();
            String gradeNamesStr = gradeModule.getGradeNamesStr();
            String[] split = gradeNamesStr.split(",");
            for (String s : split) {
                GradeClass gc = new GradeClass();
                gc.setGradeName(s);
                List<GradeClass> byGradeClass = gradeClassDao.getByGradeClass(gc);
                for (GradeClass gradeClass : byGradeClass) {
                    Integer id = gradeClass.getId();
                    ids.add(id);
                }
            }
            gradeModule.setClassesStr(ids.toString());
        }
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
