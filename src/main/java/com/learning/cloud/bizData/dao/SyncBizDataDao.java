package com.learning.cloud.bizData.dao;

import com.learning.cloud.bizData.entity.SyncBizData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SyncBizDataDao {
    int insert(SyncBizData record);

    List<SyncBizData> getBizData(Integer bizType);

    List<SyncBizData> getByBizData(SyncBizData syncBizData);

    SyncBizData getForSuiteTicket();

    List<SyncBizData> getAllBizData();

    //印证推送相同corpId的数据覆盖后status重新为0
    int updateStatus(Long id);
}
