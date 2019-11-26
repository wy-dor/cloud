package com.learning.cloud.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.learning.cloud.excel.service.ImportService;
import com.learning.cloud.gradeModule.dao.GradeEntryDao;
import com.learning.cloud.gradeModule.dao.GradeModuleDao;
import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.cloud.gradeModule.service.GradeEntryService;
import com.learning.domain.JsonResult;
import com.learning.utils.CommonUtils;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.DecimalFormat;
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
    public JsonResult importExcelGradeEntries(MultipartFile file, Long moduleId){
        try {
            if (file == null) {
                throw new Exception("文件不能为空");
            }
            GradeModule gradeModule = gradeModuleDao.getById(moduleId);
            if(gradeModule == null){
                throw new Exception("当下没有成绩");
            }

            //将classId:className重新存储为className:classId方便根据班级名称存储classId
            String classesStr = gradeModule.getClassesStr();
            if(classesStr.equals("")){
                throw new Exception("该成绩发布下没有班级");
            }
            Map<String, String> parse = (Map<String, String>) JSON.parse(classesStr);
            Map<String,String> classMap = new HashMap<>();
            Set<String> keySet = parse.keySet();
            for (String classId : keySet) {
                String className = parse.get(classId);
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
                    subject = subject.substring(0,index);
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
                //判断学号，学号能为空
                //getCellValue中判断如果为空则返回空字符串
                String stuNo = getCellValue(r.getCell(2));
                String remark = getCellValue(r.getCell(lastCellNum - 1));
                ge.setModuleId(moduleId);
                ge.setClassId(classId);
                ge.setStudentNo(stuNo);
                ge.setStudentName(stuName);
                ge.setRemark(remark);

                //对成绩进行处理
                for (int i = 3; i < lastCellNum - 1; i++) {
                    Map<String,String> map = new HashMap<>();
                    String value = getCellValue(r.getCell(i));
                    String courseName = subjectList.get(i - 3);
                    map.put("courseName",courseName);
                    map.put("value",value);
                    markMapList.add(map);
                }
                String marks = JSON.toJSONString(markMapList);

                //保存成绩
                gradeEntryService.saveEntryMarks(ge, marks);
            }
        } catch (Exception e) {
            return JsonResultUtil.error(0,e.getMessage());
        }

        return JsonResultUtil.success("录入成功");
    }

    private static String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC://数字
                    value = cell.getNumericCellValue() + "";
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = CommonUtils.timeFormat(date, null);
                        } else {
                            value = "";
                        }
                    } else {
                        value = new DecimalFormat("#.##").format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING://字符串
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_FORMULA://公式
                    value = cell.getCellFormula() + "";
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN://boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_BLANK://空值
                    value = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR://错误
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }
        }
        return value.trim();
    }
}
