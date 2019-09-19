package com.learning.cloud.duty.service.impl;

import com.learning.cloud.duty.dao.DutyRecordDao;
import com.learning.cloud.duty.entity.DutyRecord;
import com.learning.cloud.duty.service.DutyRecordService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: yyt
 * @Date: 2019-09-16 15:41
 * @Desc:
 */
@Service
public class DutyRecordServiceImpl implements DutyRecordService {

    @Autowired
    private DutyRecordDao dutyRecordDao;

    @Override
    public JsonResult addDutyRecord(DutyRecord dutyRecord) {
        int i = dutyRecordDao.addDutyRecord(dutyRecord);
        if(i>0){
            return JsonResultUtil.success(dutyRecord.getId());
        }else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult deleteDutyRecord(Long id) throws Exception {
        int i = dutyRecordDao.deleteDutyRecord(id);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.DELETE_ERROR);
        }
    }

    @Override
    public JsonResult updateDutyRecord(DutyRecord dutyRecord) throws Exception {
        int i = dutyRecordDao.updateDutyRecord(dutyRecord);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.UPDATENONE);
        }
    }

    @Override
    public JsonResult getDutyRecordByClassId(DutyRecord dutyRecord) throws Exception {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        List<DutyRecord> dutyRecords = dutyRecordDao.getDutyRecordByClassId(dutyRecord);
        //对结果按时间分组，返回数组
        HashSet set = new HashSet();
        for(int i=0;i<dutyRecords.size();i++){
            c1.setTime(dutyRecords.get(i).getDay());
            List<DutyRecord> duty = new ArrayList<>();
            for(int j=0;j<dutyRecords.size();j++){
                c2.setTime(dutyRecords.get(j).getDay());
                if(c1.getTime().equals(c2.getTime())){
                    duty.add(dutyRecords.get(j));
                }
            }
            set.add(duty);
        }
        return JsonResultUtil.success(set);
    }

    // 批量新增积分记录
    @Override
    public JsonResult addDutyRecordList(List<DutyRecord> dutyRecordList) throws Exception {
        dutyRecordDao.addDutyRecordList(dutyRecordList);
        return JsonResultUtil.success();
    }
}
