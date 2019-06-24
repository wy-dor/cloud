package com.learning.cloud.ding.onlineSign.dao;

import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.user.parent.entity.Parent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SignDao {

    int addSignTask(Sign sign);

    int signName(SignRecord signRecord);

    List<Sign> getValidTaskList(Integer classId);

    List<Sign> getAllTasks(Integer classId);


    int setStateInvalid(Integer signId);

    List<Sign> getUnsignedTasks(Parent parent);

    Sign getById(Integer id);

    Integer getRecordNum(Integer signId);

    List<SignRecord> getRecordsBySignId(Integer signId);
}
