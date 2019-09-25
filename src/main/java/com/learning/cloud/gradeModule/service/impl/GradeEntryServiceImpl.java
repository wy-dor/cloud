package com.learning.cloud.gradeModule.service.impl;

import com.alibaba.fastjson.JSON;
import com.learning.cloud.gradeModule.dao.GradeEntryDao;
import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeEntryJsonStr;
import com.learning.cloud.gradeModule.service.GradeEntryService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.CommonUtils;
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
public class GradeEntryServiceImpl implements GradeEntryService {

    @Autowired
    private GradeEntryDao gradeEntryDao;

    @Override
    public JsonResult saveGradeEntry(GradeEntryJsonStr gradeEntryJsonStr) {
        Long moduleId = gradeEntryJsonStr.getModuleId();
        Integer classId = gradeEntryJsonStr.getClassId();
        String jsonStr = gradeEntryJsonStr.getJsonStr();
        List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(jsonStr);
        for (Map<String, Object> map : parse) {
            GradeEntry gradeEntry = new GradeEntry();
            gradeEntry.setModuleId(moduleId);
            gradeEntry.setClassId(classId);
            Long id = null;
            Object idObj = map.get("id");
            if(idObj != null){
                if(idObj instanceof Integer){
                    int i = (Integer) idObj;
                    id = Long.valueOf(i);
                }else if(idObj instanceof Long){
                    id = (Long)idObj;
                }
            }
            gradeEntry.setId(id);
            String remark = (String) map.get("remark");
            Integer studentId = (Integer) map.get("studentId");
            String studentName = (String) map.get("studentName");
            //List<Map<String, Object>> marksList = (List<Map<String, Object>>) map.get("marks");
            String marks = (String) map.get("marks");
            gradeEntry.setRemark(remark);
            gradeEntry.setStudentId(studentId);
            gradeEntry.setStudentName(studentName);
            gradeEntry.setClassId(classId);
            /*StringBuffer sb = new StringBuffer();
            for (Map<String, Object> stringObjectMap : marksList) {
                String subjectName =(String)stringObjectMap.get("name");
                String scoreValue =(String)stringObjectMap.get("value");
                sb.append(subjectName+":"+scoreValue);
            }*/
            saveEntryMarks(gradeEntry, studentId, marks);

        }
        return JsonResultUtil.success(moduleId);
    }

    @Override
    public void saveEntryMarks(GradeEntry gradeEntry, Integer studentId, String marks) {
        Long entryId = gradeEntry.getId();
        if(entryId == null || entryId == 0){
            gradeEntry.setMarks(marks);
            gradeEntryDao.insert(gradeEntry);
        }else{
            GradeEntry entry = gradeEntryDao.getById(entryId);
            //获取该生之前保存的成绩
            //[{"name":"语文","value":"89"},{"name":"数学","value":"100"},{"name":"英语","value":"95"}]
            gradeEntry.setId(entryId);
            String m = entry.getMarks();
            List<Map<String, String>> mapList0 = (List<Map<String, String>>) JSON.parse(m);
            List<String> subjectNames0 = new ArrayList<>();
            Map<String,String> originMap = new HashMap<>();
            for (Map<String, String> map0 : mapList0) {
                String name = map0.get("courseName");
                String value = map0.get("value");
                subjectNames0.add(name);
                originMap.put(name,value);
            }
            //新录入的成绩
            List<Map<String,String>> mapList1 =(List<Map<String,String>>) JSON.parse(marks);
            List<String> subjectNames1 = new ArrayList<>();
            for (Map<String, String> map1 : mapList1) {
                String name = map1.get("courseName");
                subjectNames1.add(name);
            }
            //比较得出保留的录入成绩
            List<String> remainSubjects = CommonUtils.removeStringDupsInList(subjectNames0, subjectNames1);
            List<Map<String,String>> mapList2 = new ArrayList<>();
            if(subjectNames1.size() != 0){
                if(subjectNames0.size() != 0){
                    for (String remainSubject : remainSubjects) {
                        Map<String,String> tempMap = new HashMap<>();
                        tempMap.put("courseName",remainSubject);
                        tempMap.put("value",originMap.get(remainSubject));
                        mapList2.add(tempMap);
                    }
                    //保留成绩添加到录入成绩中
                    mapList1.addAll(mapList2);
                }
                String marksNewJson = JSON.toJSONString(mapList1);
                gradeEntry.setMarks(marksNewJson);
            }
            gradeEntryDao.update(gradeEntry);

        }
    }

    @Override
    public JsonResult getGradeEntryById(Long id) {
        GradeEntry gradeEntry = gradeEntryDao.getById(id);
        return JsonResultUtil.success(gradeEntry);
    }

    @Override
    public JsonResult getByGradeEntry(GradeEntry gradeEntry) {
        List<GradeEntry> gradeEntryList = gradeEntryDao.getByGradeEntry(gradeEntry);
        return JsonResultUtil.success(new PageEntity<>(gradeEntryList));
    }


}
