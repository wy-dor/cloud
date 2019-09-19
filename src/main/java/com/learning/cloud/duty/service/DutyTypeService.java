package com.learning.cloud.duty.service;

import com.learning.cloud.duty.entity.DutyType;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;

/**
 * @Author: yyt
 * @Date: 2019-09-16 09:17
 * @Desc:
 */
public interface DutyTypeService {
    JsonResult addDutyType(DutyType dutyType)throws Exception;

    JsonResult deleteDutyType(Long id)throws Exception;

    JsonResult updateDutyType(DutyType dutyType)throws Exception;

    JsonResult getDutyTypeBySchoolId(DutyType dutyType)throws Exception;

    JsonResult getDutyTypeById(Long id)throws Exception;
}
