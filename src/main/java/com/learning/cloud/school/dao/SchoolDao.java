package com.learning.cloud.school.dao;

import com.learning.cloud.school.entity.School;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SchoolDao {
    int insert(School record);

    List<School> getBySchool(School school);

    int update(School school);

    List<School> getSchools();

    String getCorpIdBySchoolName(String schoolName);

    School getSchoolByCorpId(String corpId);

    School getBySchoolId(Integer schoolId);
}
