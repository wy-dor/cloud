package com.learning.cloud.index.dao;

import com.learning.cloud.index.entity.AuthAppInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AuthAppInfoDao {
    AuthAppInfo findByCorpId(String corpId);

    int insert(AuthAppInfo authAppInfo);

    int update(AuthAppInfo authAppInfo);

    String getCorpAccessToken(String coprId);

    List<AuthAppInfo> getToAuthorize();
}
