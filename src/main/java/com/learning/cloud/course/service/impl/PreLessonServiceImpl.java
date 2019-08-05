package com.learning.cloud.course.service.impl;

import com.learning.cloud.course.dao.PreLessonDao;
import com.learning.cloud.course.entity.PreLesson;
import com.learning.cloud.course.service.PreLessonService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
