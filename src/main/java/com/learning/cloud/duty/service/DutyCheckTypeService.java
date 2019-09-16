package com.learning.cloud.duty.service;

import com.learning.cloud.duty.entity.DutyCheckType;
import com.learning.utils.JsonResultUtil;

/**
 * @Author: yyt
 * @Date: 2019-09-16 10:33
 * @Desc:
 */
public interface DutyCheckTypeService {
    JsonResultUtil addDutyCheckType(DutyCheckType dutyCheckType)throws Exception;
}
