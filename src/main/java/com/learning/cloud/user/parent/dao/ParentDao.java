package com.learning.cloud.user.parent.dao;

import com.learning.cloud.user.parent.entity.Parent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ParentDao {
    Parent getByUserId(String userId);

    Integer getClassParentNum(String classId);

    Parent getParentInClass(Parent parent);

    List<Integer> getParentIdListInClass(String classId);

    List<String> listParentUserIdInClass(Integer classId);

    Parent getParentInSchool(Parent parent);

    int insert(Parent record);

    int update(Parent parent);

    List<Parent> getParents(Long schoolId);

    int delete(Integer id);

    int deleteByClassId(String classId);
}
