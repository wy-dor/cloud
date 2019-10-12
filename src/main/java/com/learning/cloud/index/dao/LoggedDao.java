package com.learning.cloud.index.dao;

import com.learning.cloud.index.entity.LoggedRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yyt
 * @Date: 2019-10-11 15:38
 * @Desc:
 */
@Repository
@Mapper
public interface LoggedDao {

    int addLoggedRecord(LoggedRecord loggedRecord);

    int getActiveUserByCorpId(String corpId);
}
