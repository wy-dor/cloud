package com.learning.cloud.duty.controller;

import com.learning.cloud.duty.entity.DutyType;
import com.learning.cloud.duty.service.DutyTypeService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-09-16 09:10
 * @Desc: 值日检查类型，大类
 */
@RestController
public class DutyTypeController {

    @Autowired
    private DutyTypeService dutyTypeService;

    @PostMapping("/addDutyType")
    public JsonResult addDutyType(DutyType dutyType)throws Exception{
        return dutyTypeService.addDutyType(dutyType);
    }

    @PostMapping("/deleteDutyType")
    public JsonResult deleteDutyType(Long id)throws Exception{
        return dutyTypeService.deleteDutyType(id);
    }

    @PostMapping("/updateDutyType")
    public JsonResult updateDutyType(DutyType dutyType)throws Exception{
        return dutyTypeService.updateDutyType(dutyType);
    }

    @GetMapping("/getDutyTypeBySchoolId")
    public JsonResult getDutyTypeBySchoolId(DutyType dutyType)throws Exception{
        return dutyTypeService.getDutyTypeBySchoolId(dutyType);
    }

    @GetMapping("/getDutyTypeById")
    public JsonResultUtil getDutyTypeById(Long id)throws Exception{
        return dutyTypeService.getDutyTypeById(id);
    }
}
