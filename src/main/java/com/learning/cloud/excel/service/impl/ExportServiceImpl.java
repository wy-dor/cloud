package com.learning.cloud.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.excel.entity.DownloadBean;
import com.learning.cloud.excel.service.ExportService;
import com.learning.cloud.gradeModule.dao.GradeEntryDao;
import com.learning.cloud.gradeModule.dao.GradeModuleDao;
import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.domain.JsonResult;
import com.learning.utils.CommonUtils;
import com.learning.utils.JsonResultUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private GradeModuleDao gradeModuleDao;

    @Autowired
    private GradeEntryDao gradeEntryDao;

    private static final String A0 = "填写须知：\n" +
            "<1> 学号是钉钉中设置的学生学号，若未设置，仅在表格中填写无效；班级中若有学生重名，请在钉钉中设置学号或调整姓名，并在表格中作相应调整\n" +
            "<2> 在当前表格中填写的成绩，将覆盖系统中已录入的成绩信息；\n" +
            "<3> 表格中若出现灰底区域，表示由于权限限制，该班级该课程不可录入成绩，系统将不会导入该部分数据；\n" +
            "<4> 学生某门课程缺考，可输入“缺考”，或在可录入区域留空，系统将默认将该生该课程置为“缺考”；若某班或某课程暂不需要录入，可将该班的所有行或该课程的列删除；\n" +
            "<5> 填写的分值或等级，需要与成绩表设置的分值或等级匹配。";


    @Override
    public JsonResult exportExcelModule(GradeModule gradeModule) throws IOException {
        FileOutputStream out = null;
        //获取班级
        String classesStr = gradeModule.getClassesStr();
        Map<String, String> classMap = (Map<String, String>) JSON.parse(classesStr);
        Set<String> classIdStrs = classMap.keySet();
        List<Student> stuList = new ArrayList<>();
        for (String classIdStr : classIdStrs) {
            Integer classId = Integer.parseInt(classIdStr);
            GradeClass gc = new GradeClass();
            gc.setId(classId);
            List<Student> studentList = studentDao.getClassStudents(gc);
            stuList.addAll(studentList);
        }
        /*if(stuList == null || stuList.size() == 0){
            throw new IOException("该班级下没有学生");
        }*/
        String subjects = gradeModule.getSubjects();
        List<String> subjectStrList = new ArrayList<>();
        //[{"id": 1, "status": 1, "pageNum": null, "editTime": "2019-09-17 17:32:00", "schoolId": 1, "courseName": "语文", "totalScore": 100},
        // {"id": 2, "status": 1, "pageNum": null, "editTime": "2019-09-17 17:37:13", "schoolId": 1, "courseName": "数学", "totalScore": "150"}]
        Integer scoringRoles = gradeModule.getScoringRoles();
        List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(subjects);
        for (Map<String, Object> map : parse) {
            String appendix = "";
            if(scoringRoles == 1){
                String totalScore =(String) map.get("totalScore");
                appendix = "(" + totalScore +")";
            }
            String courseName = (String) map.get("courseName");
            subjectStrList.add(courseName+appendix);
        }


        //生成模板
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建table
        HSSFSheet sheet = wb.createSheet("工作表1");
        HSSFRow row1 = sheet.createRow(0);
        row1.setHeightInPoints(140);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue(A0);
        //合并单元格
        CellRangeAddress region = new CellRangeAddress(
                0,//first row
                0,//last row
                0,//first column
                20 //last column
        );
        sheet.addMergedRegion(region);

        String row2_str = "当前成绩表计分方式为分数制，小数点后保留2位";
        if(scoringRoles == 2){
            Integer rankModule = gradeModule.getRankModule();
            if(rankModule == 1){
                row2_str = "当前成绩表计分方式为等第制，请输入“优秀 / 良好 / 合格 / 待合格/ 缺考”";
            }else{
                row2_str = "当前成绩表计分方式为等第制，请输入“A / B / C / D / 缺考”";
            }
        }
        HSSFRow row2 = sheet.createRow(1);
        HSSFCell cell_1 = row2.createCell(0);
        cell_1.setCellValue(row2_str);
        CellRangeAddress region1 = new CellRangeAddress(1, 1, 0, 20);
        sheet.addMergedRegion(region1);

        //创建其他行
        HSSFRow row3 = sheet.createRow(2);
        //创建单元格并设置单元格内容
        row3.createCell(0).setCellValue("班级");
        row3.createCell(1).setCellValue("学生");
        row3.createCell(2).setCellValue("学号");
        int subjectSize = subjectStrList.size();
        for (int i = 0; i < subjectSize; i++) {
            row3.createCell(i+3).setCellValue(subjectStrList.get(i));
        }
        row3.createCell(subjectSize+3).setCellValue("评语");

        for (int i = 0; i < stuList.size(); i++) {
            HSSFRow row_recurse = sheet.createRow(i+3);

            row_recurse.createCell(0).setCellValue(classMap.get(stuList.get(i).getClassId().toString()));
            row_recurse.createCell(1).setCellValue(stuList.get(i).getStudentName());
            row_recurse.createCell(2).setCellValue(stuList.get(i).getStudentNo());
        }

        //定义样式
        CellStyle blackStyle_r1 = wb.createCellStyle();
        //自动换行
        blackStyle_r1.setWrapText(true);
        row1.getCell(0).setCellStyle(blackStyle_r1);

        //定义样式
        CellStyle blueStyle = wb.createCellStyle();
        HSSFFont blueFont = wb.createFont();
        //颜色
        blueFont.setColor(HSSFColor.DARK_BLUE.index);
        //字体大小
        //blueFont.setFontHeightInPoints((short) 10);
        //字体
        //blueFont.setFontName("宋体");
        blueStyle.setFont(blueFont);
        row2.getCell(0).setCellStyle(blueStyle);

        //定义样式
        CellStyle blackStyle_r3 = wb.createCellStyle();
        //自动换行
//        blackStyle.setWrapText(true);
        HSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)11);
        blackStyle_r3.setFont(font);
        row3.setRowStyle(blackStyle_r3);

        //设置数值类型
        //row3.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);

        //设置列宽
        List<Integer> widthList=new ArrayList<>();
        List<Integer> tempList = Arrays.asList(20, 15, 20);
        widthList.addAll(tempList);
        for (int i = 0; i < subjectSize; i++) {
            widthList.add(15);
        }
        widthList.add(60);
        for (int i = 0; i < widthList.size(); i++) {
            sheet.setColumnWidth(i,widthList.get(i)*256);
        }


        String fileName = CommonUtils.getRandomStr()+".xls";//用随机号来存储文件，避免文件名重复
        // 生成文件 程序所在目录
        String rootPath = System.getProperty("user.dir");
        String filePath = (rootPath+"/"+fileName).replace("\\","/");

        out = new FileOutputStream(filePath);
        wb.write(out);
        out.flush();
        out.close();
        DownloadBean download = new DownloadBean();
        download.setFilePath(filePath);
        download.setTitle(gradeModule.getTitle()+"-成绩导出.xls");
        return JsonResultUtil.success(download);
    }

    @Override
    public JsonResult downloadExcelGrade(Long moduleId) throws IOException {
        FileOutputStream out = null;
        StringBuilder undoneClassMention = new StringBuilder("");
        GradeModule gradeModule = gradeModuleDao.getById(moduleId);
        //根据计分规则进行判断是否需要进行总分拼接
        String classesStr = gradeModule.getClassesStr();

        StringBuilder undoneSubjectMention = new StringBuilder("");

        //所有班级信息
        Map<String, String> classMapByModule = (Map<String, String>) JSON.parse(classesStr);
        List<Integer> fullClassIdList = new ArrayList<>();
        Set<String> keySet = classMapByModule.keySet();
        for (String key : keySet) {
            fullClassIdList.add(Integer.parseInt(key));
        }

        //所有科目信息
        List<String> fullSubjectNameList = new ArrayList<>();
        String subjects = gradeModule.getSubjects();
        List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(subjects);
        for (Map<String, Object> map : parse) {
            String courseName = (String) map.get("courseName");
            fullSubjectNameList.add(courseName);
        }

        //所有被选择录入过的班级，对应班级中被录入过的科目
        List<GradeEntry> doneClassSubjectInModule = gradeEntryDao.getDoneClassSubjectInModule(moduleId);
        List<Integer> doneClassIdList = new ArrayList<>();
        //"提示：XXX班的数学，语文成绩还没有录入，请继续录入"
        for (GradeEntry ge : doneClassSubjectInModule) {
            Integer classId = ge.getClassId();
            //已完成成绩录入班级添加
            doneClassIdList.add(classId);
            //班级名称，用于拼接
            String className = classMapByModule.get(classId.toString());
            List<String> doneSubjectList = new ArrayList<>();
            String marks = ge.getMarks();
            List<Map<String, String>> par = (List<Map<String, String>>) JSON.parse(marks);
            for (Map<String, String> stringStringMap : par) {
                String courseName = stringStringMap.get("courseName");
                doneSubjectList.add(courseName);
            }
            //对比得出班级中未录入的科目进行提示
            List<String> undoneSubjectList = CommonUtils.removeStringDupsInList(fullSubjectNameList, doneSubjectList);
            if(undoneSubjectList != null && undoneSubjectList.size() > 0){
                String s = undoneSubjectList.toString();
                s.substring(1,s.length()-2);
                undoneSubjectMention.append(className+"中"+ s +"成绩还没有录入，请继续录入/n");
            }
        }
        List<Integer> undoneClassIdList = CommonUtils.removeIntegerDupsInList(fullClassIdList, doneClassIdList);
        List<String> undoneClassNameList = new ArrayList<>();
        for (Integer undoneClassId : undoneClassIdList) {
            undoneClassNameList.add(classMapByModule.get(undoneClassId.toString()));
        }
        if(undoneClassIdList != null && undoneClassIdList.size() > 0){
            String s = undoneClassNameList.toString();
            s.substring(1,s.length()-2);
            undoneClassMention.append(s +"还没有录入成绩/n");
        }

        //如果提示均不为空字符串，则返回提示
        if (!undoneClassMention.toString().equals("") || !undoneSubjectMention.toString().equals("")){
            String s = undoneClassMention.append(undoneSubjectMention).toString();
            return JsonResultUtil.success(s);
        }

        //生成模板
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建table
        HSSFSheet sheet = wb.createSheet("工作表1");
        HSSFRow row0 = sheet.createRow(0);
        //创建单元格并设置单元格内容
        row0.createCell(0).setCellValue("班级");
        row0.createCell(1).setCellValue("学生");
        row0.createCell(2).setCellValue("学号");
        int subjectSize = fullSubjectNameList.size();
        for (int i = 0; i < subjectSize; i++) {
            row0.createCell(i+3).setCellValue(fullSubjectNameList.get(i));
        }
        row0.createCell(subjectSize+3).setCellValue("评语");
        //科目抬头
        for (int i = 0; i < subjectSize; i++) {
            String subject = fullSubjectNameList.get(i);
            row0.createCell(i+3).setCellValue(subject);
        }

        GradeEntry gradeEntry = new GradeEntry();
        gradeEntry.setModuleId(moduleId);
        List<GradeEntry> entryList = gradeEntryDao.getByGradeEntry(gradeEntry);
        for (int i = 0; i < entryList.size(); i++) {
            HSSFRow r = sheet.createRow(i + 1);
            GradeEntry ge = entryList.get(i);
            Integer classId = ge.getClassId();
            String className = classMapByModule.get(classId + "");
            String studentName = ge.getStudentName();
            String studentNo = ge.getStudentNo();
            String remark = ge.getRemark();
            r.createCell(0).setCellValue(className);
            r.createCell(1).setCellValue(studentName);
            r.createCell(2).setCellValue(studentNo);
            r.createCell(subjectSize+3).setCellValue(remark);
            for (int j = 0; j < subjectSize; j++) {
                String marks = ge.getMarks();
                String subject = fullSubjectNameList.get(j);
                List<Map<String, String>> markMapList = (List<Map<String, String>>) JSON.parse(marks);
                for (Map<String, String> markMap : markMapList) {
                    if(markMap.get("courseName").equals(subject)){
                        String value = markMap.get("value");
                        if(value.equals("")){
                            value = "缺考";
                        }
                        r.createCell(j+3).setCellValue(value);
                    }
                }
            }
        }

        //定义样式
        CellStyle blackStyle = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)11);
        blackStyle.setFont(font);
        row0.setRowStyle(blackStyle);

        //设置列宽
        List<Integer> widthList=new ArrayList<>();
        List<Integer> tempList = Arrays.asList(20, 15, 30);
        widthList.addAll(tempList);
        for (int i = 0; i < subjectSize; i++) {
            widthList.add(15);
        }
        widthList.add(60);
        for (int i = 0; i < widthList.size(); i++) {
            sheet.setColumnWidth(i,widthList.get(i)*256);
        }

        String fileName = CommonUtils.getRandomStr()+".xls";//用随机号来存储文件，避免文件名重复
        // 生成文件 程序所在目录
        String rootPath = System.getProperty("user.dir");
        String filePath = (rootPath+"/"+fileName).replace("\\","/");

        out = new FileOutputStream(filePath);
        wb.write(out);
        out.flush();
        out.close();
        DownloadBean download = new DownloadBean();
        download.setFilePath(filePath);
        download.setTitle(gradeModule.getTitle()+"-成绩导出.xls");
        return JsonResultUtil.success(download);
    }

    @Override
    public JsonResult downloadExcelByName(HttpServletResponse response, String filePath, String title) throws Exception{
        OutputStream out = null;
        InputStream in = null;

        File file = new File(filePath);
        try {
            if(file.exists()){
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment; filename="+title);
                in = new FileInputStream(file);
                byte[] by = new byte[in.available()];
                in.read(by);
                out = response.getOutputStream();
                out.write(by);

                //删除该文件
                file.delete();

            }
        }catch (Exception e){
            throw new Exception("成绩模板下载失败");
        }finally {
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){
                throw new Exception("成绩模板下载失败");
            }
        }
        return JsonResultUtil.success();
    }

}
