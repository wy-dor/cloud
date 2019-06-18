package com.learning.cloud.index.dao;

import com.learning.cloud.index.entity.AuthCorpInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AuthCorpInfoDao {

    int insert(AuthCorpInfo authCorpInfo);

    AuthCorpInfo getCorpInfoByCorpId(String corpId);

    int update(AuthCorpInfo authCorpInfo);

    List<AuthCorpInfo> getCorpInfos();
}
