package com.learning.cloud.bizData.dao;

import com.learning.cloud.bizData.entity.SyncBizData;
import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SyncBizDataDao {
    int insert(SyncBizData record);

    List<SyncBizData> getBizData(@Param("subscribeId") String subscribeId, @Param("bizType") Integer bizType);

    SyncBizData getForSuiteTicket();

    List<SyncBizData> getAllBizData();

    //印证推送相同corpId的数据覆盖后status重新为0
    int updateStatus(Long id);
}
