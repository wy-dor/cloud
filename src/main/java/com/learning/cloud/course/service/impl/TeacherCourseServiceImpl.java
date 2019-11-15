package com.learning.cloud.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.learning.cloud.course.dao.CourseTypeDao;
import com.learning.cloud.course.dao.TeacherCourseDao;
import com.learning.cloud.course.entity.CourseSectionArray;
import com.learning.cloud.course.entity.CourseType;
import com.learning.cloud.course.entity.SectionArray;
import com.learning.cloud.course.entity.TeacherCourse;
import com.learning.cloud.course.service.TeacherCourseService;
import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.excel.entity.DownloadBean;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.MyException;
import com.learning.utils.CommonUtils;
import com.learning.utils.JsonResultUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: yyt
 * @Date: 2019/11/11 11:26 上午
 * @Desc:
 */
@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {

    @Autowired
    private TeacherCourseDao teacherCourseDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private CourseTypeDao courseTypeDao;

    private static final String A0 = "录入须知：\n" +
            "<1> 如果老师任课多门课程请填写老师的主课名称\n" +
            "现有课程如下：";

    //导出excel
    @Override
    public JsonResult getTeacherCourseTemplet(Long schoolId) throws Exception {
        FileOutputStream out = null;
        //获取老师任课信息
        if (schoolId == null) {
            throw new MyException(JsonResultEnum.COURSE_NO_PARAMS);
        }
        List<Teacher> teachers = teacherDao.ListTeacherInSchool(schoolId.intValue());
        List<CourseType> courseTypes = courseTypeDao.getSchoolCourseType(schoolId);

        String s = courseTypes.stream().map(CourseType::getCourseName).collect(Collectors.joining( ";"));
        //生成模板
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建table
        HSSFSheet sheet = wb.createSheet("工作表1");
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeightInPoints(90);
        HSSFCell cell = row0.createCell(0);
        cell.setCellValue(A0+s);
        //合并单元格
        CellRangeAddress region = new CellRangeAddress(
                0,//first row
                0,//last row
                0,//first column
                1 //last column
        );
        sheet.addMergedRegion(region);

        //循环创建表格内容
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            String teacherName = teacher.getTeacherName();

            //创建列头
            String[] header = {"老师名称", "课程名称"};
            //节次时间周一周二周三周四周五周六周日
            HSSFRow row2x = sheet.createRow(1);
            for (int j = 0; j < header.length; j++) {
                row2x.createCell(j).setCellValue(header[j]);
            }

            HSSFRow row3x = sheet.createRow(i + 2);
            row3x.createCell(0).setCellValue(teacherName);
            row3x.createCell(1).setCellValue(teacher.getCourseName());
        }
        //定义说明定义样式
        CellStyle A0Style = wb.createCellStyle();
        //自动换行
        A0Style.setWrapText(true);
        row0.getCell(0).setCellStyle(A0Style);


        //设置列宽
        int[] width = {20, 20};
        for (int i = 0; i < width.length; i++) {
            sheet.setColumnWidth(i, width[i] * 256);
        }

        //生成文件
        String fileName = CommonUtils.getRandomStr() + ".xls";//用随机号来存储文件，避免文件名重复
        // 生成文件 程序所在目录
        String rootPath = System.getProperty("user.dir");
        String filePath = (rootPath + "/" + fileName).replace("\\", "/");

        out = new FileOutputStream(filePath);
        wb.write(out);
        out.flush();
        out.close();
        DownloadBean download = new DownloadBean();
        download.setFilePath(filePath);
        download.setTitle("老师任课信息模版.xls");
        return JsonResultUtil.success(download);
    }

    //导入excel
    @Override
    public JsonResult importTeacherCourse(MultipartFile file, Long schoolId) throws Exception {
        StringBuilder s = new StringBuilder();
        if (file == null) {
            throw new MyException(JsonResultEnum.COURSE_NO_FILE);
        }
        //处理文件
        InputStream in = file.getInputStream();
        //excel
        Workbook workbook = WorkbookFactory.create(in);
        Sheet sheet = workbook.getSheetAt(0);
        //获取学校所有老师
        List<Teacher> teachers = teacherDao.ListTeacherInSchool(schoolId.intValue());
        Map<String, Integer> teacherNameList = teachers.stream().collect(Collectors.toMap(Teacher::getTeacherName, Teacher::getId));
        //获取所有课程类型
        List<CourseType> courseTypes = courseTypeDao.getSchoolCourseType(schoolId);
        Map<String, Long> courseList = courseTypes.stream().collect(Collectors.toMap(CourseType::getCourseName, CourseType::getId));
        //从第三行开始
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
                Cell cellTeacher = row.getCell(0);
                Cell cellCourse = row.getCell(1);
                if(cellCourse==null){
                    throw new MyException(JsonResultEnum.TEACHER_NO_COURSE);
                }
                if (cellTeacher != null) {
                    String teacherName = cellTeacher.getStringCellValue();
                    String courseName = cellCourse.getStringCellValue();
                    Long courseType = courseList.get(courseName);
                    Integer teacherId = teacherNameList.get(teacherName);
                    Teacher teacher = new Teacher();
                    teacher.setId(teacherId);
                    teacher.setCourseType(courseType);
                    teacherDao.update(teacher);
                }
        }
        return JsonResultUtil.success();
    }
}
