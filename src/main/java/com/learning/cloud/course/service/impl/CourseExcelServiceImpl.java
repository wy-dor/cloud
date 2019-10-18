package com.learning.cloud.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.learning.cloud.course.dao.CourseDao;
import com.learning.cloud.course.dao.CourseDetailDao;
import com.learning.cloud.course.dao.CourseSectionDao;
import com.learning.cloud.course.dao.CourseTypeDao;
import com.learning.cloud.course.entity.*;
import com.learning.cloud.course.service.CourseExcelService;
import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: yyt
 * @Date: 2019/10/15 10:13 上午
 * @Desc:
 */
@Service
public class CourseExcelServiceImpl implements CourseExcelService {

    @Autowired
    private CourseSectionDao courseSectionDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private CourseTypeDao courseTypeDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CourseDetailDao courseDetailDao;

    private static final String A0 = "录入须知：\n" +
            "<1> 请严格根据下载课表模板时间段填写\n" +
            "<2> 填写课表前请先维护好班级对应的老师任教信息\n";

    @Override
    public JsonResult exportCourseTemplet(String classIds, Long schoolId) throws Exception {
        FileOutputStream out = null;
        //获取班级信息，老师任课信息，学校作息时间
        if (classIds == null && schoolId == null) {
            throw new MyException(JsonResultEnum.COURSE_NO_PARAMS);
        }
        CourseSectionArray courseSectionArray = courseSectionDao.getSchoolSectionArray(schoolId);
        List<SectionArray> sectionArrays = JSONObject.parseArray(courseSectionArray.getContent(), SectionArray.class);
        if (sectionArrays == null) {
            throw new MyException(JsonResultEnum.COURSE_NO_SECTION);
        }
        //下载班级课表
        List<GradeClass> classList = null;
        if (classIds == null) {
            //所有班级
            Campus campus = new Campus();
            campus.setSchoolId(schoolId.intValue());
            classList = gradeClassDao.getClassesByCampusId(campus);
        } else {
            //对应班级
            String[] classId = classIds.split(",");
            for (String id : classId) {
                GradeClass gradeClass = gradeClassDao.getById(Integer.valueOf(id));
                classList.add(gradeClass);
            }
        }

        int size = sectionArrays.size() + 3;//加一行空白行
        //生成模板
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建table
        HSSFSheet sheet = wb.createSheet("工作表1");
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeightInPoints(90);
        HSSFCell cell = row0.createCell(0);
        cell.setCellValue(A0);
        //合并单元格
        CellRangeAddress region = new CellRangeAddress(
                0,//first row
                0,//last row
                0,//first column
                7 //last column
        );
        sheet.addMergedRegion(region);

        //循环创建表格内容
        for (int i = 0; i < classList.size(); i++) {
            GradeClass gc = classList.get(i);
            String name = gc.getGradeName() + gc.getClassName();

            //创建班级抬头
            int a = size * i + 1;
            System.out.println("班级抬头" + a);
            HSSFRow row1_x = sheet.createRow(size * i + 1);
            HSSFCell cell1_x = row1_x.createCell(0);
            cell1_x.setCellValue(name);
            CellRangeAddress region1_x = new CellRangeAddress(
                    size * i + 1,//first row
                    size * i + 1,//last row
                    0,//first column
                    7 //last column
            );
            sheet.addMergedRegion(region1_x);
            //定义班级字段样式
            CellStyle blueStyle = wb.createCellStyle();
            HSSFFont blueFont = wb.createFont();
            //颜色
            blueFont.setColor(HSSFColor.DARK_BLUE.index);
            blueStyle.setFont(blueFont);
            row1_x.getCell(0).setCellStyle(blueStyle);

            //创建列头
            String[] header = {"节次时间", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
            //节次时间周一周二周三周四周五周六周日
            HSSFRow row2x = sheet.createRow(size * i + 2);
            for (int j = 0; j < header.length; j++) {
                row2x.createCell(j).setCellValue(header[j]);
            }

            for (int j = 0; j < sectionArrays.size(); j++) {
                HSSFRow row3x = sheet.createRow(size * i + 3 + j);
                row3x.createCell(0).setCellValue(sectionArrays.get(j).getName() + "\n" + sectionArrays.get(j).getTime());
            }
            //留一行空白
            HSSFRow row4_x = sheet.createRow(size * (i + 1));
            HSSFCell cell4_x = row4_x.createCell(0);
            cell4_x.setCellValue("");
            CellRangeAddress region4_x = new CellRangeAddress(
                    size * (i + 1),//first row
                    size * (i + 1),//last row
                    0,//first column
                    7 //last column
            );
            sheet.addMergedRegion(region4_x);

            //定义空白行样式
            CellStyle grayStyle = wb.createCellStyle();
            //自动换行
            grayStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  //填充单元格
            grayStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);    //填红色
            row4_x.getCell(0).setCellStyle(grayStyle);

        }
        //定义说明定义样式
        CellStyle A0Style = wb.createCellStyle();
        //自动换行
        A0Style.setWrapText(true);
        row0.getCell(0).setCellStyle(A0Style);


        //设置列宽
        int[] width = {30, 15, 15, 15, 15, 15, 15, 15};
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
        download.setTitle("课表模板.xls");
        return JsonResultUtil.success(download);
    }

    //导入excel
    @Override
    public JsonResult importCourseTemplet(MultipartFile file, Long schoolId) throws Exception {
        StringBuilder s = new StringBuilder();
        if (file == null) {
            throw new MyException(JsonResultEnum.COURSE_NO_FILE);
        }
        //获取学校所有班级
        GradeClass gradeClass = new GradeClass();
        gradeClass.setSchoolId(schoolId.intValue());
        List<GradeClass> gc = gradeClassDao.getByGradeClass(gradeClass);
        Map<String, Integer> gcMap = gc.stream().collect(Collectors.toMap(GradeClass -> GradeClass.getGradeName() + GradeClass.getClassName(), GradeClass::getId));
        CourseSectionArray courseSectionArray = courseSectionDao.getSchoolSectionArray(schoolId);
        List<SectionArray> sectionArrays = JSONObject.parseArray(courseSectionArray.getContent(), SectionArray.class);
        int size = sectionArrays.size() + 3;
        //获取所有课程类型
        List<CourseType> courseTypes = courseTypeDao.getSchoolCourseType(schoolId);
        Map<String, Long> courseList = courseTypes.stream().collect(Collectors.toMap(CourseType::getCourseName, CourseType::getId));
        //处理文件
        InputStream in = file.getInputStream();
        //excel
        Workbook workbook = WorkbookFactory.create(in);
        Sheet sheet = workbook.getSheetAt(0);
        Integer[] weekDay = {1, 2, 3, 4, 5, 6, 7};

        //按照行循环，取出所有班级
        List<ClassNameId> classNameIdList = new ArrayList<>();
        for (int i = 1; i < sheet.getLastRowNum() - 3; i++) {
            Row row = sheet.getRow(i);
            if (i % size == 1) {//班级名称ij
                Cell cell = row.getCell(0);
                if (cell != null) {
                    ClassNameId classNameAndId = new ClassNameId();
                    String name = cell.getStringCellValue();
                    //根据班级名称获取班级id
                    if(gcMap.get(name)==null){
                        s.append(name+"的班级信息不存在;");
                    }else {
                        Integer classId = gcMap.get(name);
                        classNameAndId.setName(name);
                        classNameAndId.setClassId(classId);
                        classNameIdList.add(classNameAndId);
                    }
                }
            }
        }
        //循环处理班级
        for (int i = 0; i < classNameIdList.size(); i++) {
            Integer classId = classNameIdList.get(i).getClassId();
            String className = classNameIdList.get(i).getName();

            //获取老师
            GradeClass gcForT = new GradeClass();
            gcForT.setId(classId);
            List<Teacher> teachers = teacherDao.getClassTeachers(gcForT);
            //保存对象
            Map<Long, Integer> teacherCourse = teachers.stream().collect(Collectors.toMap(Teacher::getCourseType, Teacher::getId));
            Map<Long, String> teacherName = teachers.stream().collect(Collectors.toMap(Teacher::getCourseType, Teacher::getTeacherName));
            //获取课程主表信息
            Course course = courseDao.getCourseByClassId(classId.longValue());

            //处理excel表格
            //第四行，依次取周一~周日数据
            for (int k = 0; k < sectionArrays.size(); k++) {
                SectionArray section = sectionArrays.get(k);
                String courseTime = section.getTime();
                for (int j = 1; j <= weekDay.length; j++) {
                    Row row = sheet.getRow(i * size + k + 3);
                    int rowLength = row.getLastCellNum();
                    if (rowLength > 1) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            //表格框中的课程名称，若包含"/"则课程类型为单双周。
                            String courseName = cell.getStringCellValue();
                            String courseNameS = "";
                            if (courseName != null && !courseName.isEmpty()) {
                                if(courseName.contains("/")){
                                    courseNameS = courseName.substring(courseName.indexOf("/")+1);
                                    courseName = courseName.substring(0,courseName.indexOf("/"));
                                }
                                //初始化
                                CourseDetail courseDetail = new CourseDetail();
                                courseDetail.setClassId(classId.longValue());
                                courseDetail.setCourseId(course.getId());
                                //获取课程名称和对应老师名称
                                if(courseList.get(courseName)==null){
                                    s.append("名称为：'"+courseName+"'的课程不存在;");
                                }else {
                                    Long courseType = courseList.get(courseName);
                                    courseDetail.setCourseName(courseName);
                                    courseDetail.setCourseType(courseType);
                                    //根据课程类型获取老师信息
                                    if(teacherName.get(courseType)==null){
                                        s.append(className+courseName+"课的老师未设置;");
                                    }else {
                                        courseDetail.setCourseTeacherName(teacherName.get(courseType));
                                        courseDetail.setCourseTeacherId(teacherCourse.get(courseType).longValue());
                                    }
                                    courseDetail.setCourseTime(courseTime);
                                    courseDetail.setWeekDay(j);
                                    courseDetail.setCourseNum(k + 1);
                                    if(!courseNameS.isEmpty()){
                                        Long courseTypeS = courseList.get(courseNameS);
                                        courseDetail.setCourseNameS(courseNameS);
                                        courseDetail.setCourseTypeS(courseTypeS);
                                        courseDetail.setCourseTeacherNameS(teacherName.get(courseTypeS));
                                        courseDetail.setCourseTeacherIdS(teacherCourse.get(courseTypeS).longValue());
                                        courseDetail.setWeekType((short)4);
                                    }
                                    courseDetailDao.addCourseDetail(courseDetail);
                                }
                            }
                        }
                    }
                }
            }
        }
        return JsonResultUtil.success();
    }

    class ClassNameId {
        private String name;
        private Integer classId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getClassId() {
            return classId;
        }

        public void setClassId(Integer classId) {
            this.classId = classId;
        }
    }
}
