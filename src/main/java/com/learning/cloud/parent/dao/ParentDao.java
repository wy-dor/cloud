package com.learning.cloud.parent.dao;

import com.learning.cloud.parent.entity.Parent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ParentDao {
    int insert(Parent record);

}
