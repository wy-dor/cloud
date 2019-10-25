package com.learning.cloud.workProcess.controller;

import com.learning.cloud.workProcess.service.ProcessService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-10-08 16:18
 * @Desc: 教育局发布公文，流转到学校。
 */
@RestController
public class ProcessController {

    @Autowired
    private ProcessService processService;

    /**
     * 1.普通公文，教育局内部流转
     * 2.组织公文，教育局审核后在各个学校自动发起管理员公文，由指定用户审批或评论后确认。
     */
    @PostMapping("/createProcessExample")
    public JsonResult createProcessExample(String corpId)throws Exception{
        //创建固定格式，填充对应字段值
        return processService.createProcessExample(corpId);
    }

    //获取公文流转需要的组织
    @GetMapping("/getSchoolByBureau")
    public JsonResult getSchoolByBureau(Integer id)throws Exception{
        return processService.getSchoolByBureau(id);
    }

    //获取流程模板信息
    @GetMapping("/getProcessById")
    public JsonResult getProcessById(Integer id)throws Exception{
        return processService.getProcessById(id);
    }

    //获取教育局的流程信息
    @GetMapping("/getProcessByBureauId")
    public JsonResult getProcessByBureauId(String bureauId)throws Exception{
        return processService.getProcessByBureauId(bureauId);
    }


}
