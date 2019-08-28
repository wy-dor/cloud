package com.learning.cloud.gradeModule.service.impl;

import com.alibaba.fastjson.JSON;
import com.learning.cloud.gradeModule.dao.GradeEntryDao;
import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.service.GradeEntryService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GradeEntryServiceImpl implements GradeEntryService {

    @Autowired
    private GradeEntryDao gradeEntryDao;

    @Override
    public JsonResult addGradeEntry(GradeEntry gradeEntry) {
        Long moduleId = gradeEntry.getModuleId();
        Integer classId = gradeEntry.getClassId();
        List<GradeEntry> entryList = gradeEntryDao.getByClassModule(moduleId,classId);
        String jsonStr = gradeEntry.getJsonStr();
        List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(jsonStr);
        for (Map<String, Object> map : parse) {
            String remark = (String) map.get("remark");
            Integer studentId = (Integer) map.get("studentId");
            String studentName = (String) map.get("studentName");
            //List<Map<String, Object>> marksList = (List<Map<String, Object>>) map.get("marks");
            String marks = (String) map.get("marks");
            gradeEntry.setRemark(remark);
            gradeEntry.setStudentId(studentId);
            gradeEntry.setStudentName(studentName);
            /*StringBuffer sb = new StringBuffer();
            for (Map<String, Object> stringObjectMap : marksList) {
                String subjectName =(String)stringObjectMap.get("name");
                String scoreValue =(String)stringObjectMap.get("value");
                sb.append(subjectName+":"+scoreValue);
            }*/
            //已录入成绩保存过
            if(entryList != null && entryList.size() > 0){
                //新录入的成绩
                List<Map<String,Object>> mapList1 =(List<Map<String,Object>>) JSON.parse(marks);
                List<String> subjectNames1 = new ArrayList<>();
                for (Map<String, Object> map1 : mapList1) {
                    String name = (String) map1.get("name");
                    subjectNames1.add(name);
                }
                //获取该生之前保存的成绩
                //[{"name":"语文","value":"89"},{"name":"数学","value":"100"},{"name":"英语","value":"95"}]
                GradeEntry ge = gradeEntryDao.getByStudentModule(gradeEntry);
                String m = ge.getMarks();
                List<Map<String, Object>> mapList0 = (List<Map<String, Object>>) JSON.parse(m);
                List<String> subjectNames0 = new ArrayList<>();
                for (Map<String, Object> map0 : mapList0) {
                    String name = (String) map0.get("name");
                    subjectNames0.add(name);
                }
            }else{
                gradeEntry.setMarks(marks);
                gradeEntryDao.insert(gradeEntry);

            }
        }
        return JsonResultUtil.success(gradeEntry.getModuleId());
    }

    @Override
    public JsonResult getGradeEntryById(Long id) {
        GradeEntry gradeEntry = gradeEntryDao.getById(id);
        return JsonResultUtil.success(gradeEntry);
    }

    @Override
    public JsonResult getAllGradeEntry(GradeEntry gradeEntry) {
        List<GradeEntry> gradeEntryList = gradeEntryDao.getAllGradeEntry(gradeEntry);
        return JsonResultUtil.success(new PageEntity<>(gradeEntryList));
    }


}
