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

    int updateRole5ToOtherRole(User user);

    User getByUserId(String userId);

    User getBySchoolRoleIdentity(User user);

    int updateSupervisor(User user);

    List<User> getUserByUserIdAndCorpId(@Param("userId") String userId, @Param("corpId") String corpId);

    List<User> getUserRole234(@Param("userId") String userId, @Param("corpId") String corpId);

    int updateWithSpecificRole(User user);
}
