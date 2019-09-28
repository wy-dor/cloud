package com.learning.cloud.dept.topApi.dao;

import com.learning.cloud.dept.topApi.entity.EduPeriod;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EduPeriodDao {

    List<EduPeriod> getByPeriod(EduPeriod eduPeriod);

    int insert(EduPeriod eduPeriod);

    int update(EduPeriod eduPeriod);
}
