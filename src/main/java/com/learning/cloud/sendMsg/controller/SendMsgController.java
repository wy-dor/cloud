package com.learning.cloud.sendMsg.controller;

import com.learning.cloud.sendMsg.entity.MsgInfo;
import com.learning.cloud.sendMsg.service.SendMsgService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-09-17 18:08
 * @Desc:
 */
@RestController
public class SendMsgController {
    @Autowired
    private SendMsgService sendMsgService;

    /**
     * 签字消息
     * 发送工作消息给对应班级内的家长
     * 内容为签字链接，指点打开可以签字
     */
    @PostMapping("/sendSignLink")
    public JsonResult sendSignLink(String classIds, Integer signId, MsgInfo msgInfo) throws Exception {
        return sendMsgService.sendSignLink(classIds, signId, msgInfo);

    }

    @PostMapping("/sendDutyMsgToAdvisor")
    public JsonResult sendDutyMsgToAdvisor(Integer classId, String date, MsgInfo msgInfo) throws Exception {
        return sendMsgService.sendDutyMsgToAdvisor(classId, date, msgInfo);
    }

    @PostMapping("/sendPerformanceCard")
    public JsonResult sendPerformanceCard(String classIds, Integer moduleId, MsgInfo msgInfo) throws Exception {
        return sendMsgService.sendPerformanceCard(classIds, moduleId, msgInfo);
    }

    @PostMapping("/sendBillChargeCard")
    public JsonResult sendBillChargeCard(Integer parentId, MsgInfo msgInfo) throws Exception {
        return sendMsgService.sendBillChargeCard(parentId, msgInfo);
    }

}
