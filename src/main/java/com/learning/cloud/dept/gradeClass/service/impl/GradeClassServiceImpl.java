package com.learning.cloud.dept.gradeClass.service.impl;

import com.learning.cloud.dept.campus.dao.CampusDao;
import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.gradeClass.service.GradeClassService;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GradeClassServiceImpl implements GradeClassService {

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private CampusDao campusDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private ParentDao parentDao;

    @Override
    public ServiceResult getClassesByTeacher(Teacher teacher) {
        List<GradeClass> classList = new ArrayList<>();
        String classIds = teacher.getClassIds();
        String[] idList = classIds.split(",");
        for (String idStr : idList) {
            GradeClass byId = gradeClassDao.getById(Integer.parseInt(idStr));
            if(byId != null){
                classList.add(byId);
            }
        }
        return ServiceResult.success(new PageEntity<>(classList));
    }

    @Override
    public ServiceResult getClassesByCampus(Campus campus) {
//        Integer id = campus.getId();
//        Integer schoolId = campus.getSchoolId();
//        if(id == null){
//            Campus c = campusDao.getSchoolCampuses(schoolId).get(0);
//            campus.setId(c.getId());
//        }
        List<GradeClass> classList = gradeClassDao.getClassesByCampusId(campus);
        for (GradeClass gc : classList) {
            Integer classId = gc.getId();
            Integer teacherNum = teacherDao.getClassTeacherNum(classId);
            gc.setTeacherNum(teacherNum);
            Integer stuNum = studentDao.getClassStuNum(classId);
            gc.setStuNum(stuNum);
            Integer parentNum = parentDao.getClassParentNum(classId);
            gc.setParentNum(parentNum);
        }
        return ServiceResult.success(new PageEntity<>(classList));
    }

    @Override
    public JsonResult getClassDetails(Integer classId) {
        Map<String,Integer> map = new HashMap<>();
        Integer teacherNum = teacherDao.getClassTeacherNum(classId);
        map.put("teacherNum",teacherNum);
        Integer stuNum = studentDao.getClassStuNum(classId);
        map.put("stuNum",stuNum);
        Integer parentNum = parentDao.getClassParentNum(classId);
        map.put("parentNum",parentNum);
        return JsonResultUtil.success(map);
    }

    @Override
    public JsonResult getByGradeClass(GradeClass gradeClass) {
        List<GradeClass> classes = gradeClassDao.getByGradeClass(gradeClass);
        return JsonResultUtil.success(new PageEntity<>(classes));
    }

    @Override
    public JsonResult getGradeClassById(Integer id) {
        GradeClass byId = gradeClassDao.getById(id);
        return JsonResultUtil.success(byId);
    }

    @Override
    public JsonResult getAllGradeName(Integer schoolId) {
        List<String> gradeNameList = gradeClassDao.getAllGradeName(schoolId);
        return JsonResultUtil.success(gradeNameList);
    }
}
