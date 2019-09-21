package com.learning.cloud.gradeModule.service.impl;

import com.alibaba.fastjson.JSON;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.gradeClass.service.GradeClassService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GradeModuleServiceImpl implements GradeModuleService {

    @Autowired
    private GradeModuleDao gradeModuleDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private GradeClassService gradeClassService;

    @Override
    public JsonResult saveGradeModule(GradeModule gradeModule) {
        Integer classesAddingWay = gradeModule.getClassesAddingWay();
        //如果选择班级方式为年级添加
        if(classesAddingWay == 2){
            Map<String,String> map = new HashMap<>();
            List<String> gradeNamesStr = gradeModule.getGradeNamesStr();
            for (String s : gradeNamesStr) {
                if(s.contains("\"")){
                    s = s.replace("\"","");
                }
                if(s.contains("[")){
                    s = s.replace("[","");
                }else if(s.contains("]")){
                    s = s.replace("]","");
                }
                //获取分校下指定年级名称的班级列表
                Integer campusId = gradeClassService.getCampusIdForTeacher(gradeModule.getUserId(), gradeModule.getSchoolId());
                GradeClass gc = new GradeClass();
                gc.setCampusId(campusId);
                gc.setGradeName(s);
                List<GradeClass> byGradeClass = gradeClassDao.getByGradeClass(gc);
                for (GradeClass gc_1 : byGradeClass) {
                    map.put(gc_1.getId().toString(),gc_1.getClassName());
                }
            }
            String classMapStr = JSON.toJSONString(map);
            gradeModule.setClassesStr(classMapStr);
        }
        //分数制科目和总分拼接 语文（100）
        /*if(gradeModule.getScoringRoles() == 1){
            String subjects = gradeModule.getSubjects();
            List<Map<String, Object>> mapList = new ArrayList<>();
            List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(subjects);
            for (Map<String, Object> map : parse) {
                String courseName = (String) map.get("courseName");
                String totalValue = (String) map.get("totalScore");
                map.put("courseName",courseName+"("+totalValue+")");
                mapList.add(map);
            }
            String s = JSON.toJSONString(mapList);
            gradeModule.setSubjects(s);
        }*/
        Long id = gradeModule.getId();
        if(id == null){
            gradeModuleDao.insert(gradeModule);
            id = gradeModule.getId();
        }else{
            gradeModuleDao.update(gradeModule);
        }
        return JsonResultUtil.success(id);
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
