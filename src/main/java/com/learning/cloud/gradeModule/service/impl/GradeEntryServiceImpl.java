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

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GradeEntryServiceImpl implements GradeEntryService {

    @Autowired
    private GradeEntryDao gradeEntryDao;

    @Override
    public JsonResult addGradeEntry(GradeEntry gradeEntry) {
        String jsonStr = gradeEntry.getJsonStr();
        List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(jsonStr);
        for (Map<String, Object> map : parse) {
            String remark = (String) map.get("remark");
            Integer studentId = (Integer) map.get("studentId");
            String studentName = (String) map.get("studentName");
            List<Map<String, Object>> marksList = (List<Map<String, Object>>) map.get("marks");
            gradeEntry.setRemark(remark);
            gradeEntry.setStudentId(studentId);
            gradeEntry.setStudentName(studentName);
            gradeEntry.setMarks("");
        }
        gradeEntryDao.insert(gradeEntry);
        return JsonResultUtil.success(gradeEntry.getId());
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
