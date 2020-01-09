package com.learning.cloud.user.parent.dao;

import com.learning.cloud.user.parent.entity.ParentPhone;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ParentPhoneDao {
    ParentPhone getByUserId(String userId);

    ParentPhone getById(Long id);

    int insert(ParentPhone parentPhone);

    int update(ParentPhone parentPhone);

    int delete(Integer id);
}
