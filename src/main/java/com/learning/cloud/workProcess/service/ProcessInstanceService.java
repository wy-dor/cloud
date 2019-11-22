package com.learning.cloud.workProcess.service;

import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.learning.cloud.workProcess.entity.ProcessValue;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019/10/21 3:48 下午
 * @Desc:
 */
public interface ProcessInstanceService {
    JsonResult createProcessInstance(ProcessValue process)throws Exception;

    JsonResult getLastDocumentNo(Integer bureauId)throws Exception;

    OapiProcessinstanceGetResponse getInstanceStatus(String processInstanceId, String corpId)throws Exception;
}
