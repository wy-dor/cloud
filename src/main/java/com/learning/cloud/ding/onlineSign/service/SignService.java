package com.learning.cloud.ding.onlineSign.service;

import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface SignService {

    JsonResult addSignTask(Sign sign)throws Exception;

    JsonResult getAllTasks(Parent parent) throws Exception;

    JsonResult getValidTaskList(Parent parent)throws Exception;

    JsonResult signName(SignRecord signRecord, MultipartFile file)throws Exception;

    JsonResult setStateInvalid(Integer signId)throws Exception;

    JsonResult getUnsignedTasks(Parent parent) throws Exception;

    JsonResult getSignNum(Integer signId) throws Exception;

    JsonResult getRecordCount(Integer signId) throws Exception;

    JsonResult getRecordsBySignId(Sign sign) throws Exception;

    JsonResult getSignTaskById(Integer id)throws Exception;
}
