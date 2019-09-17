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
    public JsonResult sendSignLink(Integer classId, MsgInfo msgInfo)throws Exception{
        return sendMsgService.sendSignLink(classId, msgInfo);

    }

}
