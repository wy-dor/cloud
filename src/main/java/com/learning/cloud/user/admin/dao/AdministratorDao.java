package com.learning.cloud.user.admin.dao;

import com.learning.cloud.user.admin.entity.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdministratorDao {
    int insert(Administrator record);

    Administrator getByAdm(Administrator administrator);
}
