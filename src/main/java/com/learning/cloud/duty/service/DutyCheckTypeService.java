package com.learning.cloud.duty.service;

import com.learning.cloud.duty.entity.DutyCheckType;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;

/**
 * @Author: yyt
 * @Date: 2019-09-16 10:33
 * @Desc:
 */
public interface DutyCheckTypeService {
    JsonResult addDutyCheckType(DutyCheckType dutyCheckType)throws Exception;

    JsonResult deleteDutyCheckType(Long id)throws Exception;

    JsonResult updateDutyCheckType(DutyCheckType dutyCheckType)throws Exception;

    JsonResult getDutyCheckTypeById(Long id)throws Exception;

    JsonResult getDutyCheckTypeByTypeId(Long dutyTypeId)throws Exception;
}
