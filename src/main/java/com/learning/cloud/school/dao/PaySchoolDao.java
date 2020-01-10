package com.learning.cloud.school.dao;

import com.learning.cloud.bill.entity.AmountItems;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.school.entity.PaySchool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PaySchoolDao {
    int isPaySchoolNameExist(String schoolName);

    int addPaySchool(PaySchool school);

    int updatePaySchool(PaySchool school);

    //账单接口调用学校信息
    PaySchool getPaySchoolBySchoolId(Integer schoolId);

    Integer getPaySchoolIdByNo(String schoolNo);

    int updatePaySchoolNo(@Param("schoolNo") String schoolNo, @Param("id") Integer id);

    List<Bill> getPaySchoolBillsInTime(PaySchool school);

    AmountItems getPaySchoolTotalBillAmount(PaySchool school);

    AmountItems getPaySchoolMonthlyAmount(PaySchool school);

    Integer updateAuthK12(Integer schoolId);
}
