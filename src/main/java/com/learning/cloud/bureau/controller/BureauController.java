package com.learning.cloud.bureau.controller;

import com.learning.cloud.bureau.service.BureauService;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BureauController {

    @Autowired
    private BureauService bureauService;

    //获取所有的教育局列表
    @GetMapping("/api/getBureaus")
    public ServiceResult getBureaus(){
        return ServiceResult.success(bureauService.getBureaus());
    }

    /*返回组织的bureauId*/
    @GetMapping("/api/getBureauIdByCorpId")
    public JsonResult getBureauIdByCorpId(String corpId){
        return JsonResultUtil.success(bureauService.getOrgInfoByCorpId(corpId));
    }
}
