package com.learning.cloud.duty.controller;

import com.learning.cloud.duty.dao.DutyRecordDao;
import com.learning.cloud.duty.entity.DutyRecord;
import com.learning.cloud.duty.service.DutyRecordService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-09-16 15:41
 * @Desc:
 */
@RestController
public class DutyRecordController {
    @Autowired
    private DutyRecordService dutyRecordService;

    @PostMapping("/addDutyRecord")
    public JsonResult addDutyRecord(DutyRecord dutyRecord)throws Exception{
        return dutyRecordService.addDutyRecord(dutyRecord);
    }

    @PostMapping("/deleteDutyRecord")
    public JsonResult deleteDutyRecord(Long id)throws Exception{
        return dutyRecordService.deleteDutyRecord(id);
    }

    @PostMapping("/updateDutyRecord")
    public JsonResult updateDutyRecord(DutyRecord dutyRecord)throws Exception{
        return dutyRecordService.updateDutyRecord(dutyRecord);
    }

    @PostMapping("/getDutyRecordByClassId")
    public JsonResult getDutyRecordByClassId(DutyRecord dutyRecord)throws Exception{
        return dutyRecordService.getDutyRecordByClassId(dutyRecord);
    }


}
