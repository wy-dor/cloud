package com.learning.cloud.supervision.dao;

import com.learning.cloud.supervision.entity.Inspector;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-11 23:41
 * @Desc:
 */
@Mapper
@Repository
public interface InspectorDao {
    int addInspector(Inspector inspector);

    int deleteInspector(Long id);

    int updateInspector(Inspector inspector);

    List<Inspector> getInspector(Inspector inspector);

    Inspector loginInspector(@Param("login") String login, @Param("password") String password);
}
