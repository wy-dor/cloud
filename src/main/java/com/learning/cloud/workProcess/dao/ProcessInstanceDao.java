package com.learning.cloud.workProcess.dao;

import com.learning.cloud.workProcess.entity.ProcessInstance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yyt
 * @Date: 2019/10/24 5:31 下午
 * @Desc:
 */
@Mapper
@Repository
public interface ProcessInstanceDao {
    int insert(ProcessInstance processInstance);

    int update(ProcessInstance processInstance);
}
