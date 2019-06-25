package com.learning.cloud.dept.campus.dao;

import com.learning.cloud.dept.campus.entity.Campus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CampusDao {

    int insert(Campus record);

    Campus getCampus(Campus campus);

    List<Campus> getSchoolCampuses(Integer schoolId);

    int updateCampusType(Integer id);
}
