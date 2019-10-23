package com.learning.cloud.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.learning.cloud.course.dao.CourseSectionDao;
import com.learning.cloud.course.entity.CourseSection;
import com.learning.cloud.course.entity.CourseSectionArray;
import com.learning.cloud.course.entity.SectionArray;
import com.learning.cloud.course.service.CourseSectionService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-06-12 15:09
 * @Desc:
 */
@Service
public class CourseSectionServiceImpl implements CourseSectionService {

    @Autowired
    private CourseSectionDao courseSectionDao;

    @Override
    public JsonResult addSection(CourseSection courseSection) throws Exception {
        int i = courseSectionDao.addSection(courseSection);
        return JsonResultUtil.success(courseSection.getId());
    }

    @Override
    public JsonResult editSection(CourseSection courseSection) throws Exception {
        int i = courseSectionDao.editSection(courseSection);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public JsonResult getSchoolSection(Long id) throws Exception {
        List<CourseSection> courseSections = courseSectionDao.getSchoolSection(id);
        return JsonResultUtil.success(courseSections);
    }

    /**
     * 保存学校的节次数组，方便展示时获取
     */
    @Override
    public JsonResult saveSectionArray(Long id, String name) throws Exception {
        List<CourseSection> courseSections = courseSectionDao.getSchoolSection(id);
        List<SectionArray> sectionArrays = new ArrayList<>();
        if(courseSections!=null&&courseSections.size()>0){
            //存储为json数组
            for(CourseSection bean : courseSections){
                SectionArray sa = new SectionArray();
                sa.setName(bean.getName());
                sa.setTime(bean.getStart()+"-"+bean.getEnd());
                sectionArrays.add(sa);
            }
        }
        String sectionArray = JSON.toJSONString(sectionArrays);
        //存储section_array
        int i = courseSectionDao.saveSectionArray(id, sectionArray, name);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult deleteSection(Long id) throws Exception {
        int i = courseSectionDao.deleteSection(id);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult deleteSchoolSection(Long sectionId) throws Exception {
        int i = courseSectionDao.deleteSchoolSection(sectionId);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult getSchoolSectionArray(Long schoolId) throws Exception {
        CourseSectionArray courseSectionArray = courseSectionDao.getSchoolSectionArray(schoolId);
        return JsonResultUtil.success(courseSectionArray);
    }

    @Override
    public JsonResult getSchoolSectionList(Long schoolId) throws Exception {
        List<CourseSectionArray> courseSectionArrays = courseSectionDao.getSchoolSectionList(schoolId);
        return JsonResultUtil.success(courseSectionArrays);
    }

    //启用作息时间
    @Override
    public JsonResult setSectionArrayActive(Long id, Long schoolId) throws Exception {
        int i = courseSectionDao.setSectionArrayBlock(id, schoolId);
        if(i>=0){
            int j = courseSectionDao.setSectionArrayActive(id);
            if(j>0){
                return JsonResultUtil.success();
            }else {
                return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
            }
        }else {
            return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public JsonResult addSectionArray(CourseSectionArray courseSectionArray) throws Exception {
        int i = courseSectionDao.addSectionArray(courseSectionArray);
        if(i>0){
            return JsonResultUtil.success(courseSectionArray.getId());
        }else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }
}
