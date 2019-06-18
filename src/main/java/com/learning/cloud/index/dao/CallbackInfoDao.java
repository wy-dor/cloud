package com.learning.cloud.index.dao;

import com.learning.cloud.index.entity.CallbackInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CallbackInfoDao {
    String getAuthCodeByCorpId(String corpId);

    int insert(CallbackInfo callbackInfo);

    int update(CallbackInfo callbackInfo);

}
