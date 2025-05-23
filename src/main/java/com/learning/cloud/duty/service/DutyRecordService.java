package com.learning.cloud.duty.service;

import com.learning.cloud.duty.entity.DutyRecord;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: yyt
 * @Date: 2019-09-16 15:41
 * @Desc:
 */
public interface DutyRecordService {
    JsonResult setTeachersForDutyCheck(String teacherIds, Integer dutyInspector);

    JsonResult addDutyRecord(DutyRecord dutyRecord) throws Exception;

    JsonResult addPics(List<MultipartFile> fileList) throws Exception;

    JsonResult deleteDutyRecord(Long id) throws Exception;

    JsonResult updateDutyRecord(DutyRecord dutyRecord) throws Exception;

    JsonResult getDutyRecordByClassId(DutyRecord dutyRecord) throws Exception;

    JsonResult addDutyRecordList(List<DutyRecord> dutyRecordList) throws Exception;

    Map<String, Object> getDutyRecordStatistics(Integer schoolId, String gradeName, String startTime, String endTime);
}
