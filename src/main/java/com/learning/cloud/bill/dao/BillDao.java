package com.learning.cloud.bill.dao;

import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bill.entity.BillSearch;
import com.learning.cloud.bill.entity.SumBillType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface BillDao {
    int addBill(Bill bill);

    void updateBillById(@Param("orderNo") String orderNo, @Param("sendTime") Date sendTime, @Param("id") Integer id, @Param("isvTradeNo") String isvTradeNo);

    void updateBillStateById(@Param("tradeStatus") String tradeStatus, @Param("id") Integer id);

    int getCountByParentId(Integer parentId);

    void deleteBillByParentId(Integer id)throws Exception;

    List<SumBillType> getSchoolBillInPeriod(BillSearch billSearch);

    List<Bill> getSchoolBills(Bill bill);

    List<Bill> getSchoolBillsByParent(Integer id);

    Bill getBillById(Integer id);

    int updateQueryStatus(@Param("order_status") String order_status, @Param("out_trade_no") String out_trade_no, @Param("id") Integer id);

    void updateCallBackByOrderNo(@Param("tradeNo") String tradeNo, @Param("gmtPayment") String gmtPayment, @Param("totalAmount") String totalAmount, @Param("outTradeNo") String outTradeNo)throws Exception;

    Bill getBillByOrderNo(String orderNo);

    List<Bill> getUnConfirmedBills();
}
