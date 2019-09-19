package com.learning.cloud.duty.controller;

import com.learning.cloud.duty.entity.DutyCheckType;
import com.learning.cloud.duty.service.DutyCheckTypeService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public JsonResult addDutyCheckType(DutyCheckType dutyCheckType)throws Exception{
        return dutyCheckTypeService.addDutyCheckType(dutyCheckType);
    }

    @PostMapping("/deleteDutyCheckType")
    public JsonResult deleteDutyCheckType(Long id)throws Exception{
        return dutyCheckTypeService.deleteDutyCheckType(id);
    }

    @PostMapping("/updateDutyCheckType")
    public JsonResult updateDutyCheckType(DutyCheckType dutyCheckType)throws Exception{
        return dutyCheckTypeService.updateDutyCheckType(dutyCheckType);
    }

    @GetMapping("/getDutyCheckTypeByTypeId")
    public JsonResult getDutyCheckTypeByTypeId(DutyCheckType dutyCheckType)throws Exception{
        return dutyCheckTypeService.getDutyCheckTypeByTypeId(dutyCheckType);
    }

    @GetMapping("/getDutyCheckTypeById")
    public JsonResult getDutyCheckTypeById(Long id)throws Exception{
        return dutyCheckTypeService.getDutyCheckTypeById(id);
    }
}
