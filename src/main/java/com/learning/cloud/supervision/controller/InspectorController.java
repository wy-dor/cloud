package com.learning.cloud.supervision.controller;

import com.learning.cloud.supervision.entity.Inspector;
import com.learning.cloud.supervision.service.InspectorService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @Author: yyt
 * @Date: 2019-09-11 17:42
 * @Desc: 督学账号管理，督学登录
 */
@RestController
public class InspectorController {
    @Autowired
    private InspectorService inspectorService;

    // 新增督学
    @PostMapping("/addInspector")
    public JsonResult addInspector(Inspector inspector)throws Exception{
        return inspectorService.addInspector(inspector);
    }

    // 删除督学
    @PostMapping("/deleteInspector")
    public JsonResult deleteInspector(Long id)throws Exception{
        return inspectorService.deleteInspector(id);
    }

    // 修改督学
    @PostMapping("/updateInspector")
    public JsonResult updateInspector(Inspector inspector)throws Exception{
        return inspectorService.updateInspector(inspector);
    }

    // 查看督学
    @GetMapping("/getInspector")
    public JsonResult getInspector(Inspector inspector)throws Exception{
        return inspectorService.getInspector(inspector);
    }

    // 督学登录
    @PostMapping("/loginInspector")
    public JsonResult loginInspector(@RequestParam(value="login",required = true) String login,
                                     @RequestParam(value="password",required = true) String password)throws Exception{
        return inspectorService.loginInspector(login, password);
    }

}
