package com.learning.cloud.ding.onlineSign.service.impl;

import com.learning.cloud.ding.onlineSign.dao.SignDao;
import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.ding.onlineSign.service.SignService;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SignServiceImpl implements SignService {
    @Autowired
    private SignDao signDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private QuestionService questionService;

    /*新增签字任务*/
    @Override
    public JsonResult addSignTask(Sign sign) throws Exception {
        int i = signDao.addSignTask(sign);
        //插入需要签字的家长id和家长名称
        return JsonResultUtil.success(sign.getId());
    }

    @Override
    public JsonResult getAllTasks(Parent parent) throws Exception {
        List<Sign> taskList = signDao.getAllTasks(parent);
        return JsonResultUtil.success(new PageEntity<>(taskList));
    }

    /*查找家长对应有效的班级签字任务*/
    @Override
    public JsonResult getValidTaskList(Parent parent) {
        List<Sign> taskList = signDao.getValidTaskList(parent);
        return JsonResultUtil.success(new PageEntity<>(taskList));
    }

    /*家长进行签字*/
    @Override
    public JsonResult signName(SignRecord signRecord, MultipartFile file) throws Exception {
        if(file != null){
            Long picId = questionService.reduceImg(file);
            signRecord.setPicId(picId);
        }
        signDao.signName(signRecord);
        return JsonResultUtil.success();
    }

    /*关闭签字任务*/
    @Override
    public JsonResult setStateInvalid(Integer signId) throws Exception {
        signDao.setStateInvalid(signId);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult getUnsignedTasks(Parent parent) throws Exception{
        List<Sign> signList = new ArrayList<>();
        signList = signDao.getUnsignedTasks(parent);
        return JsonResultUtil.success(new PageEntity<>(signList));
    }

    @Override
    public JsonResult getSignNum(Integer signId) throws Exception {
        Sign byId = signDao.getById(signId);
        String classIds = byId.getClassIds();
        String[] idStrs = classIds.split(",");
        int num = 0;
        for (String idStr : idStrs) {
            int id = Integer.parseInt(idStr);
            Integer stuNum = studentDao.getClassStuNum(id);
            num += stuNum;
        }
        return JsonResultUtil.success(num);
    }

    @Override
    public JsonResult getRecordCount(Integer signId) throws Exception {
        Integer count = signDao.getRecordNum(signId);
        return JsonResultUtil.success(count);
    }

    @Override
    public JsonResult getRecordsBySignId(Sign sign) throws Exception {
        List<SignRecord> recordList = signDao.getRecordsBySignId(sign);
        return JsonResultUtil.success(new PageEntity<>(recordList));
    }

    @Override
    public JsonResult getSignTaskById(Integer id) throws Exception {
        Sign sign = signDao.getById(id);
        return JsonResultUtil.success(sign);
    }

    @Override
    public JsonResult getIsSignFlag(String userId, Integer signId) throws Exception {
        Sign sign = signDao.getIsSignFlag(userId, signId);
        return JsonResultUtil.success(sign);
    }
}
