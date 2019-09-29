package com.learning.cloud.gradeModule.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.learning.cloud.gradeModule.dao.GradeEntryDao;
import com.learning.cloud.gradeModule.dao.GradeModuleDao;
import com.learning.cloud.gradeModule.entity.GradeEntry;
import com.learning.cloud.gradeModule.entity.GradeEntryJsonStr;
import com.learning.cloud.gradeModule.entity.GradeModule;
import com.learning.cloud.gradeModule.service.GradeEntryService;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.CommonUtils;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GradeEntryServiceImpl implements GradeEntryService {

    @Autowired
    private GradeEntryDao gradeEntryDao;

    @Autowired
    private GradeModuleDao gradeModuleDao;

    @Autowired
    private StudentDao studentDao;

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
            if (idObj != null) {
                if (idObj instanceof Integer) {
                    int i = (Integer) idObj;
                    id = Long.valueOf(i);
                } else if (idObj instanceof Long) {
                    id = (Long) idObj;
                }
            }
            gradeEntry.setId(id);
            String remark = (String) map.get("remark");
            String studentNo = (String) map.get("studentNo");
            String studentName = (String) map.get("studentName");
            //List<Map<String, Object>> marksList = (List<Map<String, Object>>) map.get("marks");
            String marks = (String) map.get("marks");
            gradeEntry.setRemark(remark);
            gradeEntry.setStudentNo(studentNo);
            gradeEntry.setStudentName(studentName);
            gradeEntry.setClassId(classId);
            /*StringBuffer sb = new StringBuffer();
            for (Map<String, Object> stringObjectMap : marksList) {
                String subjectName =(String)stringObjectMap.get("name");
                String scoreValue =(String)stringObjectMap.get("value");
                sb.append(subjectName+":"+scoreValue);
            }*/
            saveEntryMarks(gradeEntry, marks);

        }
        return JsonResultUtil.success(moduleId);
    }

    @Override
    public void saveEntryMarks(GradeEntry gradeEntry, String marks) {
        List<GradeEntry> byGradeEntry = gradeEntryDao.getByGradeEntry(gradeEntry);
        Long entryId = null;
        if(byGradeEntry != null && byGradeEntry.size() > 0){
            entryId  = byGradeEntry.get(0).getId();
        }
        if (entryId == null || entryId == 0) {
            //空字符串替换为"缺考"
            List<Map<String, String>> mapListForReplace = new ArrayList<>();
            List<Map<String, String>> mapList = (List<Map<String, String>>) JSON.parse(marks);
            for (Map<String, String> map : mapList) {
                String value = map.get("value");
                if (value.equals("")){
                    map.put("value","缺考");
                }
                mapListForReplace.add(map);
            }
            marks = JSON.toJSONString(mapListForReplace);
            gradeEntry.setMarks(marks);
            gradeEntryDao.insert(gradeEntry);
        } else {
            GradeEntry entry = gradeEntryDao.getById(entryId);
            //获取该生之前保存的成绩
            //[{"name":"语文","value":"89"},{"name":"数学","value":"100"},{"name":"英语","value":"95"}]
            gradeEntry.setId(entryId);
            String m = entry.getMarks();
            List<Map<String, String>> mapList0 = (List<Map<String, String>>) JSON.parse(m);
            List<String> subjectNames0 = new ArrayList<>();
            Map<String, String> originMap = new HashMap<>();
            for (Map<String, String> map0 : mapList0) {
                String name = map0.get("courseName");
                String value = map0.get("value");
                subjectNames0.add(name);
                originMap.put(name, value);
            }
            //新录入的成绩
            List<Map<String, String>> mapList1 = (List<Map<String, String>>) JSON.parse(marks);
            List<String> subjectNames1 = new ArrayList<>();
            for (Map<String, String> map1 : mapList1) {
                String name = map1.get("courseName");
                subjectNames1.add(name);
            }
            //比较得出保留的录入成绩
            List<String> remainSubjects = CommonUtils.removeStringDupsInList(subjectNames0, subjectNames1);
            List<Map<String, String>> mapList2 = new ArrayList<>();
            if (subjectNames1.size() != 0) {
                if (subjectNames0.size() != 0) {
                    for (String remainSubject : remainSubjects) {
                        Map<String, String> tempMap = new HashMap<>();
                        tempMap.put("courseName", remainSubject);
                        String value = originMap.get(remainSubject);
                        if(value.equals("")){
                            value = "缺考";
                        }
                        tempMap.put("value", value);
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
    public JsonResult getGradeEntryStatistics(GradeEntry gradeEntry) {
        //获取模板中的科目信息
        GradeModule byId = gradeModuleDao.getById(gradeEntry.getModuleId());
        String subjects = byId.getSubjects();
//        Map<String, Object>[] subjectMapArr = (Map<String, Object>[]) JSON.parse(subjects);
        List<HashMap> hashMaps = JSONArray.parseArray(subjects, HashMap.class);
        int subjectCounts = hashMaps.size();
        Map<String, Object>[] subjectMapArr = new Map[subjectCounts];
        for (int i = 0; i < subjectCounts; i++) {
            subjectMapArr[i] = hashMaps.get(i);
        }

        //获取模板下的所有成绩记录
        List<GradeEntry> gradeEntries = gradeEntryDao.getByGradeEntry(gradeEntry);
        int entrySize = gradeEntries.size();

        //科目数组
        String[] subjectArr = new String[subjectCounts];

        for (int i = 0; i < subjectCounts; i++) {
            String courseName = subjectMapArr[i].get("courseName").toString();
            subjectArr[i] = courseName;
        }

        //统计数组
        Map<String, String>[] statisticMapArr = null;

        //分数制
        if (byId.getScoringRoles() == 1) {
            statisticMapArr = new HashMap[subjectCounts + 1];
            //存储科目成绩进行统计
            List<Double>[] subjectMarksStrListArr = new ArrayList[subjectCounts + 1];

            //计算总分
            Integer fullScore = 0;
            for (int i = 0; i < subjectCounts; i++) {
                Integer totalScore = Integer.parseInt(subjectMapArr[i].get("totalScore").toString());
                fullScore += totalScore;
            }

            //成绩链表添加
            for (int i = 0; i < subjectCounts; i++) {
                String courseName = subjectArr[i];
                Map<String, String> tempMap = new HashMap<>();
                tempMap.put("courseName", courseName);
                String totalScore = subjectMapArr[i].get("totalScore").toString();
                tempMap.put("totalScore", totalScore);
                statisticMapArr[i] = tempMap;

                List<Double> tempList = new ArrayList<>();
                for (GradeEntry entry : gradeEntries) {
                    String marks = entry.getMarks();
                    List<Map<String, String>> parse = (List<Map<String, String>>) JSON.parse(marks);
                    for (Map<String, String> courseValueMap : parse) {
                        String course = courseValueMap.get("courseName");
                        if (courseName.equals(course)) {
                            String value = courseValueMap.get("value");
                            if (!value.equals("缺考") && ! value.equals("")) {
                                tempList.add(Double.parseDouble(value));
                            }
                            break;
                        }
                    }
                }

                subjectMarksStrListArr[i] = tempList;
            }

            //总分链表添加
            Map<String, String> fullTempMap = new HashMap<>();
            List<Double> fullTempList = new ArrayList<>();
            for (GradeEntry entry : gradeEntries) {
                String marks = entry.getMarks();
                List<Map<String, String>> parse = (List<Map<String, String>>) JSON.parse(marks);
                //总分计算
                double markSum = 0;
                for (Map<String, String> courseValueMap : parse) {
                    String value = courseValueMap.get("value");
                    if (!value.equals("缺考") && !value.equals("")) {
                        double doubleValue = Double.parseDouble(value);
                        markSum += doubleValue;
                    }
                }
                //总分链表添加
                fullTempList.add(markSum);
            }
            subjectMarksStrListArr[subjectCounts] = fullTempList;
            fullTempMap.put("courseName", "总分");
            fullTempMap.put("totalScore", fullScore.toString());
            statisticMapArr[subjectCounts] = fullTempMap;

            //统计数据保留两位有效数字
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
            //用于对应统计数组的统计成绩列表
            for (int i = 0; i < subjectMarksStrListArr.length; i++) {
                Map<String, String> tempMap = statisticMapArr[i];
                Double average = subjectMarksStrListArr[i].stream().mapToDouble(Double::doubleValue).average().getAsDouble();
                Double max = subjectMarksStrListArr[i].stream().mapToDouble(Double::doubleValue).max().getAsDouble();
                Double min = subjectMarksStrListArr[i].stream().mapToDouble(Double::doubleValue).min().getAsDouble();
                Long count = subjectMarksStrListArr[i].stream().mapToDouble(Double::doubleValue).summaryStatistics().getCount();
                tempMap.put("average", df.format(average));
                tempMap.put("max", df.format(max));
                tempMap.put("min", df.format(min));
                statisticMapArr[i] = tempMap;
            }

        } else {
            //等第制统计个数
            statisticMapArr = new HashMap[subjectCounts];

            //等级模板和个数
            Integer rankModule = byId.getRankModule();
            String[] rankStr = new String[subjectCounts];
            if (rankModule == 1) {
                String[] rank_1 = {"优秀", "良好", "合格", "待合格"};
                rankStr = rank_1;
            } else {
                String[] rank_2 = {"A", "B", "C", "D"};
                rankStr = rank_2;
            }

            //成绩链表添加
            for (int i = 0; i < subjectCounts; i++) {
                String courseName = subjectArr[i];

                Map<String, String> tempMap = new HashMap<>();
                tempMap.put("courseName", courseName);

                //每一科的成绩等级个数统计
                Integer[] rankMarksCount = {0, 0, 0, 0};

                for (GradeEntry entry : gradeEntries) {
                    String marks = entry.getMarks();
                    List<Map<String, String>> parse = (List<Map<String, String>>) JSON.parse(marks);
                    for (Map<String, String> courseValueMap : parse) {
                        String course = courseValueMap.get("courseName");
                        if (courseName.equals(course)) {
                            String value = courseValueMap.get("value");
                            if (!value.equals("缺考")) {
                                if (value.equals(rankStr[0])) {
                                    rankMarksCount[0]++;
                                } else if (value.equals(rankStr[1])) {
                                    rankMarksCount[1]++;
                                } else if (value.equals(rankStr[2])) {
                                    rankMarksCount[2]++;
                                } else {
                                    rankMarksCount[3]++;
                                }
                            }
                            break;
                        }
                    }
                }

                //个数统计
                for (int j = 0; j < 4; j++) {
                    tempMap.put(rankStr[j], rankMarksCount[j].toString());
                }

                //别忘了放入统计map中
                statisticMapArr[i] = tempMap;

            }
        }

        return JsonResultUtil.success(statisticMapArr);
    }

    @Override
    public JsonResult getGradeEntryForStudent(Long moduleId, String userId) {
        Student student = studentDao.getByUserId(userId);
        Integer classId = student.getClassId();
        String studentName = student.getStudentName();
        GradeEntry ge = new GradeEntry();
        ge.setModuleId(moduleId);
        ge.setClassId(classId);
        ge.setStudentName(studentName);
        List<GradeEntry> byGradeEntry = gradeEntryDao.getByGradeEntry(ge);
        GradeEntry gradeEntry = null;
        if (byGradeEntry != null && byGradeEntry.size() > 0) {
            gradeEntry = byGradeEntry.get(0);
        }
        return JsonResultUtil.success(gradeEntry);
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
