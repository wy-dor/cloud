package com.learning.cloud.duty.controller;

import com.learning.cloud.duty.entity.DutyCheckType;
import com.learning.cloud.duty.service.DutyCheckTypeService;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-09-16 10:33
 * @Desc:
 */

@RestController
public class DutyCheckTypeController {

    @Autowired
    private DutyCheckTypeService dutyCheckTypeService;

    @PostMapping("/addDutyCheckType")
    public JsonResultUtil addDutyCheckType(DutyCheckType dutyCheckType)throws Exception{
        return dutyCheckTypeService.addDutyCheckType(dutyCheckType);
    }
}
