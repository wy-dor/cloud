package com.learning.cloud.bizData.dao;

import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SyncBizDataMediumDao {
    int insert(SyncBizDataMedium record);

    List<SyncBizDataMedium> getBizDataMedium(@Param("subscribeId") String subscribeId, @Param("bizType") Integer bizType);

    List<SyncBizDataMedium> getAllBizDataMedium(SyncBizDataMedium syncBizDataMedium);

    int updateStatus(Long id);
}
