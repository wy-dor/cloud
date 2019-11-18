package com.learning.cloud.workProcess.dao;

import com.learning.cloud.workProcess.entity.Process;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yyt
 * @Date: 2019/10/22 1:32 下午
 * @Desc:
 */
@Mapper
@Repository
public interface ProcessDao {
    Process getProcessById(Integer id);

    void insert(Process process);

    Process getProcessByBureauId(String bureauId);

    Process getProcessByCorpId(String corpId);

    void update(Process process);

    Process getCourseProcess(String corpId);
}
