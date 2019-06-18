package com.learning.cloud.index.dao;

import com.learning.cloud.index.entity.AuthUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AuthUserInfoDao {
    int insert(AuthUserInfo authUserInfo);

    AuthUserInfo getByCorpId(String corpId);

    int update(AuthUserInfo authUserInfo);
}
