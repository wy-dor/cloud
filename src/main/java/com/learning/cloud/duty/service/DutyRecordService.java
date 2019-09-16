package com.learning.cloud.duty.service;

import com.learning.cloud.duty.entity.DutyRecord;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-09-16 15:41
 * @Desc:
 */
public interface DutyRecordService {
    JsonResult addDutyRecord(DutyRecord dutyRecord)throws Exception;

    JsonResult deleteDutyRecord(Long id)throws Exception;

    JsonResult updateDutyRecord(DutyRecord dutyRecord)throws Exception;

    JsonResult getDutyRecordByClassId(DutyRecord dutyRecord)throws Exception;
}
