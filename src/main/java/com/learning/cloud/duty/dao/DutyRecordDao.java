package com.learning.cloud.duty.dao;

import com.learning.cloud.duty.entity.DutyRecord;
import com.learning.cloud.duty.entity.RecordStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-16 16:58
 * @Desc:
 */
@Mapper
@Repository
public interface DutyRecordDao {
    int addDutyRecord(DutyRecord dutyRecord);

    int deleteDutyRecord(Long id);

    int updateDutyRecord(DutyRecord dutyRecord);

    List<DutyRecord> getDutyRecordByClassId(DutyRecord dutyRecord);

    void addDutyRecordList(List<DutyRecord> dutyRecordList);

    List<RecordStatistics> getRecordPointStatistics(String startTime, String endTime, String classIds);
}
