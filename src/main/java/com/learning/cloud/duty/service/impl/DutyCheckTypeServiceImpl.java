package com.learning.cloud.duty.service.impl;

import com.learning.cloud.duty.dao.DutyCheckTypeDao;
import com.learning.cloud.duty.entity.DutyCheckType;
import com.learning.cloud.duty.service.DutyCheckTypeService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public JsonResult addDutyCheckType(DutyCheckType dutyCheckType) throws Exception {
        int i = dutyCheckTypeDao.addDutyCheckType(dutyCheckType);
        if (i > 0) {
            return JsonResultUtil.success(dutyCheckType.getId());
        } else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult deleteDutyCheckType(Long id) throws Exception {
        int i = dutyCheckTypeDao.deleteDutyCheckType(id);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.DELETE_ERROR);
        }
    }

    @Override
    public JsonResult updateDutyCheckType(DutyCheckType dutyCheckType) throws Exception {
        int i = dutyCheckTypeDao.updateDutyCheckType(dutyCheckType);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.UPDATENONE);
        }
    }

    @Override
    public JsonResult getDutyCheckTypeByTypeId(DutyCheckType dutyCheckType) throws Exception {
        List<DutyCheckType> dutyCheckTypes = dutyCheckTypeDao.getDutyCheckTypeByTypeId(dutyCheckType);
        return JsonResultUtil.success(dutyCheckTypes);
    }

    @Override
    public JsonResult getDutyCheckTypeById(Long id) throws Exception {
        DutyCheckType dutyCheckType = dutyCheckTypeDao.getDutyCheckTypeById(id);
        return JsonResultUtil.success(dutyCheckType);
    }
}
