package com.learning.cloud.index.dao;

import com.learning.cloud.index.entity.CorpAgent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CorpAgentDao {
    int insert(CorpAgent record);

}
