package com.learning.cloud.user.user.dao;

import com.learning.cloud.user.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    int insert(User record);

    List<User> getByUnionId(String unionId);

    int update(User user);

    User getByUserId(String userId);

    User getBySchoolRoleIdentity(User user);

    int updateSupervisor(User user);

    User getUserByUserIdAndCropId(@Param("userId") String userId, @Param("cropId") String cropId);
}
