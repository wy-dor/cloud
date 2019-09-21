package com.learning.cloud.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.learning.cloud.excel.service.ImportService;
import com.learning.cloud.gradeModule.dao.GradeEntryDao;
import com.learning.cloud.gradeModule.dao.GradeModuleDao;
import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.cloud.gradeModule.service.GradeEntryService;
import com.learning.domain.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private GradeModuleDao gradeModuleDao;

    @Autowired
    private GradeEntryDao gradeEntryDao;

    @Autowired
    private GradeEntryService gradeEntryService;

    @Override
    public JsonResult importExcelGradeEntries(MultipartFile file, Long moduleId) throws Exception {
        if (file == null) {
            throw new Exception("请传入文件");
        }
        GradeModule gradeModule = gradeModuleDao.getById(moduleId);

        //将classId:className重新存储为className:classId方便根据班级名称存储classId
        String classesStr = gradeModule.getClassesStr();
        Map<String, String> parse = (Map<String, String>) JSON.parse(classesStr);
        Map<String,String> classMap = new HashMap<>();
        Set<String> keySet = parse.keySet();
        for (String className : keySet) {
            String classId = parse.get(className);
            classMap.put(className,classId);
        }
        InputStream in = file.getInputStream();
        //excel
        //Workbook workbook = new XSSFWorkbook(in);
        Workbook workbook = WorkbookFactory.create(in);
        Sheet sheet = workbook.getSheetAt(0);
        List<String> subjectList = new ArrayList<>();

        Row rowTitle = sheet.getRow(2);
        int lastCellNum = rowTitle.getLastCellNum();
        //获取科目类型
        for (int i = 3; i < lastCellNum - 1; i++) {
            //如果是分数制需要将拼接的总分去掉
            String subject = rowTitle.getCell(i).getStringCellValue();
            if(gradeModule.getScoringRoles() == 1){
                int index = subject.indexOf("(");
                subject = subject.substring(index);
            }
            subjectList.add(subject);
        }

        for (Row r : sheet) {
            if (r.getRowNum() < 3) {
                continue;
            }
            //存储成绩
            List<Map<String,String>> markMapList = new ArrayList<>();
            GradeEntry ge = new GradeEntry();
            String className = r.getCell(0).getStringCellValue().trim();
            String classIdStr = classMap.get(className);
            int classId = Integer.parseInt(classIdStr);
            String stuName = r.getCell(1).getStringCellValue().trim();
            String stuIdStr = r.getCell(2).getStringCellValue().trim();
            int stuId = Integer.parseInt(stuIdStr);
            String remark = r.getCell(lastCellNum - 1).getStringCellValue().trim();
            ge.setModuleId(moduleId);
            ge.setClassId(classId);
            ge.setStudentId(stuId);
            ge.setStudentName(stuName);
            ge.setRemark(remark);

            //对成绩进行处理
            for (int i = 3; i < lastCellNum - 1; i++) {
                Map<String,String> map = new HashMap<>();
                String value = r.getCell(i).getStringCellValue().trim();
                String courseName = subjectList.get(i - 3);
                map.put("courseName",courseName);
                map.put("value",value);
                markMapList.add(map);
            }
            String marks = JSON.toJSONString(markMapList);

            //保存成绩
            gradeEntryService.saveEntryMarks(ge, stuId, marks);
        }

        return null;
    }
}
