package com.learning.cloud.user.parent.dao;

import com.learning.cloud.user.parent.entity.Parent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ParentDao {
    int insert(Parent record);

    Parent getByUserId(String userid);

    int update(Parent parent);
}
