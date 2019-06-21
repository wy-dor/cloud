package com.learning.cloud.ding.onlineSign.service.impl;

import com.learning.cloud.ding.onlineSign.dao.SignDao;
import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.ding.onlineSign.service.SignService;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SignServiceImpl implements SignService {
    @Autowired
    private SignDao signDao;

    /*新增签字任务*/
    @Override
    public JsonResult addSignTask(Sign sign) throws Exception {
        int i = signDao.addSignTask(sign);
        //插入需要签字的家长id和家长名称

        return JsonResultUtil.success(sign.getId());
    }

    @Override
    public JsonResult getValidTaskList(Parent parent) {
        Integer classId = parent.getClassId();
        /*前端使用定时任务实现任务状态的转变*/
        List<Sign> taskList = signDao.getValidTaskList(classId);
        return JsonResultUtil.success(taskList);
    }


    /*签字*/
    @Override
    public JsonResult signName(SignRecord signRecord) throws Exception {
        int i = signDao.signName(signRecord);
        return null;
    }

    @Override
    public JsonResult setSateInvalid(Integer signId) throws Exception {
        int i = signDao.setStateInvalid(signId);
        return null;
    }
}
