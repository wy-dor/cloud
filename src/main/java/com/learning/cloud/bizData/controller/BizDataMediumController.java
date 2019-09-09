package com.learning.cloud.bizData.controller;

import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import com.learning.cloud.bizData.service.BizDataMediumService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BizDataMediumController {

    @Autowired
    private BizDataMediumService bizDataMediumService;

    @GetMapping("/initBizDataMedium")
    public JsonResult initBizDataMedium(SyncBizDataMedium syncBizDataMedium) throws Exception{
        return bizDataMediumService.initBizDataMedium(syncBizDataMedium);
    }

}
