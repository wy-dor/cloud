package com.learning.cloud.bill.dao;

import com.learning.cloud.bill.entity.PreBill;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PreBillDao {
    int addPreBill(PreBill preBill);

    List<PreBill> getByPreBill(PreBill preBill);

    int deletePreBillByParentId(Integer id)throws Exception;

    PreBill getPreBillById(Integer id);
}
