package com.learning.cloud.duty.service.impl;

import com.learning.cloud.duty.dao.DutyCheckTypeDao;
import com.learning.cloud.duty.entity.DutyCheckType;
import com.learning.cloud.duty.service.DutyCheckTypeService;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yyt
 * @Date: 2019-09-16 10:34
 * @Desc:
 */
@Service
public class DutyCheckTypeServiceImpl implements DutyCheckTypeService {

    @Autowired
    private DutyCheckTypeDao dutyCheckTypeDao;

    @Override
    public JsonResultUtil addDutyCheckType(DutyCheckType dutyCheckType) throws Exception {
        int i = dutyCheckTypeDao.addDutyCheckType(dutyCheckType);
        return null;
    }
}
