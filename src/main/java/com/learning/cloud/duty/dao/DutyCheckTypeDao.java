package com.learning.cloud.duty.dao;

import com.learning.cloud.duty.entity.DutyCheckType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-16 10:48
 * @Desc:
 */
@Mapper
@Repository
public interface DutyCheckTypeDao {
    int addDutyCheckType(DutyCheckType dutyCheckType);

    int deleteDutyCheckType(Long id);

    int updateDutyCheckType(DutyCheckType dutyCheckType);

    List<DutyCheckType> getDutyCheckTypeByTypeId(DutyCheckType dutyCheckType);

    DutyCheckType getDutyCheckTypeById(Long id);
}
