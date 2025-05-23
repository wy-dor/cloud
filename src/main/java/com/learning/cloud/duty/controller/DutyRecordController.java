package com.learning.cloud.duty.controller;

import com.learning.cloud.duty.entity.DutyRecord;
import com.learning.cloud.duty.entity.RecordStatistics;
import com.learning.cloud.duty.service.DutyRecordService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


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
    public JsonResult addDutyRecord(DutyRecord dutyRecord) throws Exception {
        return dutyRecordService.addDutyRecord(dutyRecord);
    }

    @PostMapping("/deleteDutyRecord")
    public JsonResult deleteDutyRecord(Long id) throws Exception {
        return dutyRecordService.deleteDutyRecord(id);
    }

    @PostMapping("/updateDutyRecord")
    public JsonResult updateDutyRecord(DutyRecord dutyRecord) throws Exception {
        return dutyRecordService.updateDutyRecord(dutyRecord);
    }

    @GetMapping("/getDutyRecordByClassId")
    public JsonResult getDutyRecordByClassId(DutyRecord dutyRecord) throws Exception {
        return dutyRecordService.getDutyRecordByClassId(dutyRecord);
    }

    // 批量插入记录
    @PostMapping("/addDutyRecordList")
    public JsonResult addDutyRecordList(@RequestBody List<DutyRecord> dutyRecordList) throws Exception {
        return dutyRecordService.addDutyRecordList(dutyRecordList);
    }

    @PostMapping("/addDutyPics")
    public JsonResult addDutyPics(@RequestParam(value = "fileList", required = false) List<MultipartFile> fileList) throws Exception {
        return dutyRecordService.addPics(fileList);
    }

    @PostMapping("/setTeachersForDutyCheck")
    public JsonResult setTeachersForDutyCheck(String teacherIds, Integer dutyInspector) {
        return dutyRecordService.setTeachersForDutyCheck(teacherIds, dutyInspector);
    }

    @GetMapping("/getDutyRecordStatistics")
    public JsonResult getDutyRecordStatistics(Integer schoolId, String gradeName, String startTime, String endTime) throws ParseException {
        Map<String, Object> map = dutyRecordService.getDutyRecordStatistics(schoolId, gradeName, startTime, endTime);
        return JsonResultUtil.success(map.get("rsList"));
    }

}
