package com.learning.cloud.gradeModule.service.impl;

import com.alibaba.fastjson.JSON;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.gradeClass.service.GradeClassService;
import com.learning.cloud.gradeModule.dao.GradeEntryDao;
import com.learning.cloud.gradeModule.dao.GradeModuleDao;
import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.cloud.gradeModule.service.GradeModuleService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.CommonUtils;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class GradeModuleServiceImpl implements GradeModuleService {

    @Autowired
    private GradeModuleDao gradeModuleDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private GradeClassService gradeClassService;

    @Autowired
    private GradeEntryDao gradeEntryDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public JsonResult saveGradeModule(GradeModule gradeModule) {
        Integer classesAddingWay = gradeModule.getClassesAddingWay();
        //如果选择班级方式为年级添加
        if(classesAddingWay == 2){
            Map<String,String> map = new HashMap<>();
            List<String> gradeNamesStr = gradeModule.getGradeNamesStr();
            String gradesStr = String.join(",",gradeNamesStr);
            gradeModule.setGradesStr(gradesStr);
            for (String s : gradeNamesStr) {
                if(s.contains("\"")){
                    s = s.replace("\"","");
                }
                if(s.contains("[")){
                    s = s.replace("[","");
                }
                if(s.contains("]")){
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
        String classesStr = gradeModule.getClassesStr();
        if(classesStr.equals("")){
            return JsonResultUtil.error(0,"成绩模板格式错误，班级不能为空！");
        }
        Long id = gradeModule.getId();
        if(id == null){
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            gradeModule.setCreateTime(dateFormat.format(date));
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
    public JsonResult getAllGradeModuleForAdministrator(GradeModule gradeModule){
        List<GradeModule> gradeModuleList = gradeModuleDao.getAllGradeModuleForAdministrator(gradeModule);
        return JsonResultUtil.success(new PageEntity<>(gradeModuleList));
    }

    @Override
    public JsonResult getAllGradeModule(GradeModule gradeModule) {
        List<Integer> classIds = gradeModule.getClassIds();
        if(classIds == null || classIds.size() == 0){
            return JsonResultUtil.success(null);
        }
        List<GradeModule> gradeModuleList = gradeModuleDao.getAllGradeModule(gradeModule);
        return JsonResultUtil.success(new PageEntity<>(gradeModuleList));
    }

    @Override
    public JsonResult deleteGradeModule(Long id) {
        gradeModuleDao.deleteGradeModule(id);
        gradeEntryDao.deleteByModuleId(id);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult getGradeModulesForClass(GradeEntry gradeEntry) {
        List<GradeModule> gradeModuleList = gradeModuleDao.getGradeModulesForClass(gradeEntry);
        return JsonResultUtil.success(new PageEntity<>(gradeModuleList));
    }

    @Override
    public JsonResult updateGradeModule(GradeModule gradeModule) {
        Integer status = gradeModule.getStatus();
        //成绩发布更新
        if(status != null && status == 1){
            Long moduleId = gradeModule.getId();
            GradeModule byId = gradeModuleDao.getById(moduleId);
            StringBuilder undoneClassMention = new StringBuilder("");
            //根据计分规则进行判断是否需要进行总分拼接
            String classesStr = byId.getClassesStr();

            StringBuilder undoneSubjectMention = new StringBuilder("");

            //所有班级信息
            Map<String, String> classMapByModule = (Map<String, String>) JSON.parse(classesStr);
            List<Integer> fullClassIdList = new ArrayList<>();
            Set<String> keySet = classMapByModule.keySet();
            for (String key : keySet) {
                //若班级下学生人数为0则不计入统计
                Integer classId1 = Integer.parseInt(key);
                Integer classStuNum = studentDao.getClassStuNum(classId1);
                if (classStuNum != null && classStuNum != 0) {
                    fullClassIdList.add(classId1);
                }
            }

            //所有科目信息
            List<String> fullSubjectNameList = new ArrayList<>();
            String subjects = byId.getSubjects();
            List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(subjects);
            for (Map<String, Object> map : parse) {
                String courseName = (String) map.get("courseName");
                fullSubjectNameList.add(courseName);
            }

            //所有被选择录入过的班级，对应班级中被录入过的科目
            GradeEntry tempEntry = new GradeEntry();
            tempEntry.setModuleId(moduleId);
            List<GradeEntry> doneClassSubjectInModule = gradeEntryDao.getDoneClassSubjectInModule(tempEntry);
            List<Integer> doneClassIdList = new ArrayList<>();
            //如果传入classId可该班级没有成绩录入时
            //"提示：XXX班的数学，语文成绩还没有录入，请继续录入"
            for (GradeEntry ge : doneClassSubjectInModule) {
                Integer classId2 = ge.getClassId();
                //已完成成绩录入班级添加
                doneClassIdList.add(classId2);
                //班级名称，用于拼接
                String className = classMapByModule.get(classId2.toString());
                List<String> doneSubjectList = new ArrayList<>();
                String marks = ge.getMarks();
                List<Map<String, String>> par = (List<Map<String, String>>) JSON.parse(marks);
                for (Map<String, String> stringStringMap : par) {
                    String courseName = stringStringMap.get("courseName");
                    doneSubjectList.add(courseName);
                }
                //对比得出班级中未录入的科目进行提示
                List<String> undoneSubjectList = CommonUtils.removeStringDupsInList(fullSubjectNameList, doneSubjectList);
                if (undoneSubjectList != null && undoneSubjectList.size() > 0) {
                    String s = undoneSubjectList.toString();
                    String substring = s.substring(1, s.length() - 1);
                    undoneSubjectMention.append(className + "中" + substring + "成绩还没有录入，请继续录入；");
                }
            }

            List<Integer> undoneClassIdList = CommonUtils.removeIntegerDupsInList(fullClassIdList, doneClassIdList);
            List<String> undoneClassNameList = new ArrayList<>();
            for (Integer undoneClassId : undoneClassIdList) {
                undoneClassNameList.add(classMapByModule.get(undoneClassId.toString()));
            }
            if (undoneClassIdList != null && undoneClassIdList.size() > 0) {
                String s = undoneClassNameList.toString();
                String substring = s.substring(1, s.length() - 1);
                undoneClassMention.append(substring + "还没有录入成绩；");
            }

            //如果提示均不为空字符串，则返回提示
            if (!undoneClassMention.toString().equals("") || !undoneSubjectMention.toString().equals("")) {
                String s = undoneClassMention.append(undoneSubjectMention).toString();
                return JsonResultUtil.error(0, s);
            }

            //添加发布时间
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            byId.setPublishTime(dateFormat.format(date));
        }
        gradeModuleDao.update(gradeModule);
        return JsonResultUtil.success("更新成功");
    }
}
