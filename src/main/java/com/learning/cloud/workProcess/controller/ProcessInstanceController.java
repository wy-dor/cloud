package com.learning.cloud.workProcess.controller;

import com.learning.cloud.workProcess.entity.ProcessValue;
import com.learning.cloud.workProcess.service.ProcessInstanceService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019/10/21 2:53 下午
 * @Desc: 发起流程实例，保存流程信息
 */
@RestController
public class ProcessInstanceController {

    @Autowired
    private ProcessInstanceService processInstanceService;

    //创建实例
    @PostMapping("/createProcessInstance")
    public JsonResult createProcessInstance(ProcessValue processValue)throws Exception{
        return processInstanceService.createProcessInstance(processValue);
    }

    //创建实例时获取最新的公文编号。
//    @GetMapping("/getLastDocumentNo")
//    public JsonResult getLastDocumentNo(Integer bureauId)throws Exception{
//        return processInstanceService.getLastDocumentNo(bureauId);
//    }

    @GetMapping("/getInstanceStatus")
    public JsonResult getInstanceStatus(String processInstanceId,String corpId)throws Exception{
        return processInstanceService.getInstanceStatus(processInstanceId, corpId);
    }
}
