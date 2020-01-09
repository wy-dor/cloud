package com.learning.cloud.bill.dao;

import com.learning.cloud.bill.entity.ParentBill;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
@Mapper
public interface ParentBillDao {
    ParentBill getParentBillById(Integer id);

    void deleteParentBillById(Integer id)throws Exception;

    List<ParentBill> getParentBill(ParentBill parentBill);

    int addParentBill(ParentBill parentBill);

    int updateParent(Integer id);
}
