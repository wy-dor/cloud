package com.learning.cloud.ding.onlineSign.dao;

import com.learning.cloud.ding.onlineSign.entity.Sign;
import com.learning.cloud.ding.onlineSign.entity.SignRecord;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SignDao {

    int addSignTask(Sign sign);

    int signName(SignRecord signRecord);

    List<Sign> getValidTaskList(Parent parent);

    List<Sign> getAllTasks(Parent parent);

    int setStateInvalid(Integer signId);

    List<Sign> getUnsignedTasks(Parent parent);

    Sign getById(Integer id);

    Integer getRecordNum(Integer signId);

    List<SignRecord> getRecordsBySignId(Sign sign);

    int getIsSignFlag(@Param("userId") String userId, @Param("signId") Integer signId);
}
