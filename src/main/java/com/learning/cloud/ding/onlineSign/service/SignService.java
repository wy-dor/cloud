package com.learning.cloud.ding.onlineSign.service;

import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.domain.JsonResult;

public interface SignService {

    JsonResult addSignTask(Sign sign)throws Exception;

    JsonResult getValidTaskList(Parent parent)throws Exception;

    JsonResult signName(SignRecord signRecord)throws Exception;

    JsonResult setSateInvalid(Integer signId)throws Exception;
}
