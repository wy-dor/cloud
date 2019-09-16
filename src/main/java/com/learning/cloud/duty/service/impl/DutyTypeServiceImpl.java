package com.learning.cloud.duty.service.impl;

import com.learning.cloud.duty.dao.DutyTypeDao;
import com.learning.cloud.duty.entity.DutyType;
import com.learning.cloud.duty.service.DutyTypeService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-16 09:17
 * @Desc:
 */
@Service
public class DutyTypeServiceImpl implements DutyTypeService {

    @Autowired
    private DutyTypeDao dutyTypeDao;

    @Override
    public JsonResult addDutyType(DutyType dutyType) throws Exception {
        int i = dutyTypeDao.addDutyType(dutyType);
        if(i>0){
            return JsonResultUtil.success(dutyType.getId());
        }else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult deleteDutyType(Long id) throws Exception {
        int i = dutyTypeDao.deleteDutyType(id);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.DELETE_ERROR);
        }
    }

    @Override
    public JsonResult updateDutyType(DutyType dutyType) throws Exception {
        int i = dutyTypeDao.updateDutyType(dutyType);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.UPDATENONE);
        }
    }

    @Override
    public JsonResult getDutyTypeBySchoolId(DutyType dutyType) throws Exception {
        List<DutyType> dutyTypes = dutyTypeDao.getDutyTypeBySchoolId(dutyType);
        return JsonResultUtil.success(new PageEntity<>(dutyTypes));
    }

    @Override
    public JsonResultUtil getDutyTypeById(Long id) throws Exception {
        DutyType dutyType = dutyTypeDao.getDutyTypeById(id);
        return null;
    }
}
