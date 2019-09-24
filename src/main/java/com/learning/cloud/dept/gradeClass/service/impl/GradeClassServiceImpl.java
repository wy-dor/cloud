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
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
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

    @Autowired
    private UserDao userDao;

    @Override
    public ServiceResult getClassesByTeacher(Integer teacherId) {
        List<GradeClass> classList = new ArrayList<>();
        Teacher teacher = teacherDao.getById(teacherId);
        String[] idList = teacher.getClassIds().split(",");
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
            Integer parentNum = parentDao.getClassParentNum(classId.toString());
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
        Integer parentNum = parentDao.getClassParentNum(classId.toString());
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
    public JsonResult getAllGradeName(Integer schoolId, String userId) {
        Integer campusId = getCampusIdForTeacher(userId, schoolId);
        List<String> gradeNameList = gradeClassDao.getAllGradeName(campusId);
        return JsonResultUtil.success(gradeNameList);
    }

    @Override
    public JsonResult listGradeClassByTeacherInSchool(String userId, Integer schoolId) {
        Integer campusId = getCampusIdForTeacher(userId, schoolId);
        GradeClass gradeClass = new GradeClass();
        gradeClass.setCampusId(campusId);
        List<GradeClass> byGradeClass = gradeClassDao.getByGradeClass(gradeClass);
        return JsonResultUtil.success(byGradeClass);
    }

    @Override
    public Integer getCampusIdForTeacher(String userId, Integer schoolId) {
        User user = new User();
        user.setSchoolId(schoolId);
        user.setUserId(userId);
        user.setRoleType(3);
        User bySchoolRoleIdentity = userDao.getBySchoolRoleIdentity(user);
        return bySchoolRoleIdentity.getCampusId();
    }
}
