package com.learning.cloud.ding.onlineSign.dao;

import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SignDao {

    int addSignTask(Sign sign);

    int signName(SignRecord signRecord);

    List<Sign> getValidTaskList(Integer classId);

    int setStateInvalid(Integer signId);
}
