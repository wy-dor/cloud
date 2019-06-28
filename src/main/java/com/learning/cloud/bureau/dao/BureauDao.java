package com.learning.cloud.bureau.dao;

import com.learning.cloud.bureau.entity.Bureau;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BureauDao {
    List<Bureau> getBureaus();

    int insert(Bureau record);

    Bureau getByBureauName(String bureauName);

    Bureau getByCorpId(String corpId);
}
