package com.learning.cloud.bill.dao;

import com.learning.cloud.bill.entity.PreBill;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PreBillDao {
    int addPreBill(PreBill preBill);

    void deletePreBillByParentId(Integer id)throws Exception;

    PreBill getPreBillById(Integer id);
}
