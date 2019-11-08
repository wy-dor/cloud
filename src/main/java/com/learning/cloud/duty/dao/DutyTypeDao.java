package com.learning.cloud.duty.dao;

import com.learning.cloud.duty.entity.DutyType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-16 09:19
 * @Desc:
 */
@Mapper
@Repository
public interface DutyTypeDao {
    int addDutyType(DutyType dutyType);

    int deleteDutyType(Long id);

    int updateDutyType(DutyType dutyType);

    List<DutyType> getDutyTypeBySchoolId(DutyType dutyType);

    DutyType getDutyTypeById(Long id);

    int getTotalPointInSchool(Integer schoolId);
}
