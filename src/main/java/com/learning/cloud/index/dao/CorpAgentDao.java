package com.learning.cloud.index.dao;

import com.learning.cloud.index.entity.CorpAgent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CorpAgentDao {
    CorpAgent getByCorpId(String corpId);

    int insert(CorpAgent record);

    int update(CorpAgent corpAgent);
}
