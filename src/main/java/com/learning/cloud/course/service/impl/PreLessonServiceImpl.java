package com.learning.cloud.course.service.impl;

import com.learning.cloud.course.dao.PreLessonDao;
import com.learning.cloud.course.entity.PreLesson;
import com.learning.cloud.course.service.PreLessonService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PreLessonServiceImpl implements PreLessonService {

    @Autowired
    private PreLessonDao preLessonDao;

    @Override
    public JsonResult addPreLesson(PreLesson preLesson) {
        preLessonDao.insert(preLesson);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult deletePreLesson(Long id) {
        preLessonDao.deletePreLesson(id);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult getPreLessonById(Long id) {
        PreLesson preLesson = preLessonDao.getPreLessonById(id);
        return JsonResultUtil.success(preLesson);
    }

    @Override
    public JsonResult getAllPreLessons(PreLesson preLesson) {
        List<PreLesson> preLessonList = preLessonDao.getAllPreLessons(preLesson);
        return JsonResultUtil.success(new PageEntity<>(preLessonList));
    }

}
