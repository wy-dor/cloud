package com.learning.cloud.user.admin.dao;

import com.learning.cloud.user.admin.entity.Administrator;

public interface AdministratorDao {
    int insertSelective(Administrator record);
}
