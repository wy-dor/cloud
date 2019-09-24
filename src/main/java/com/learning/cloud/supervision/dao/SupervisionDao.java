package com.learning.cloud.supervision.dao;

import com.learning.cloud.supervision.entity.Supervision;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SupervisionDao {
    int insert(Supervision record);

    List<Supervision> getSupervision(Supervision supervision);

    List<Supervision> getAllValidSupervision(Supervision supervision);

    Supervision getSupervisionById(long id);

    int update(Supervision supervision);

    int removeSupervisionById(Long id);

    int deleteSupervisionById(Long id);

    List<Supervision> getBureauSupervision(Supervision supervision);

    Supervision getToppingSupervision(Integer bureauId);

    int increaseClick(long id);

    List<Supervision> getPicsForSupervision(Supervision supervision);
}
