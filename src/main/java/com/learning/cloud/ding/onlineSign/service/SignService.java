package com.learning.cloud.ding.onlineSign.service;

import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.domain.JsonResult;

public interface SignService {

    JsonResult addSignTask(Sign sign)throws Exception;

    JsonResult getAllTasks(Parent parent) throws Exception;

    JsonResult getValidTaskList(Parent parent)throws Exception;

    JsonResult signName(SignRecord signRecord)throws Exception;

    JsonResult setStateInvalid(Integer signId)throws Exception;

    JsonResult getUnsignedTasks(Parent parent) throws Exception;

    JsonResult getSignNum(Integer signId) throws Exception;

    JsonResult getRecordCount(Integer signId) throws Exception;

    JsonResult getRecordsBySignId(Sign sign) throws Exception;

}
