package com.learning.cloud.sendMsg.service;

import com.learning.cloud.sendMsg.entity.MsgInfo;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-09-17 10:58
 * @Desc:
 */
public interface SendMsgService {
    JsonResult sendSignLink(Integer classId, MsgInfo msgInfo)throws Exception;
}
