package com.learning.cloud.user.user.dao;

import com.learning.cloud.user.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {
    int insert(User record);

    User getByUnionId(String unionId);

    int update(User user);

    User getByUserId(String userId);

    User getBySchoolRoleIdentity(User user);

    int updateToBeSupervisor(User user);
}
