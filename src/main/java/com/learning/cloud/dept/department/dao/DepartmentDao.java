package com.learning.cloud.dept.department.dao;

import com.learning.cloud.dept.department.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DepartmentDao {
    int insert(Department record);

    int update(Department department);

    Department getByDeptId(String deptId);
}
