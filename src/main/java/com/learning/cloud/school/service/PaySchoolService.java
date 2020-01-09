package com.learning.cloud.school.service;

import com.learning.cloud.school.entity.PaySchool;
import com.learning.domain.JsonResult;

public interface PaySchoolService {
    JsonResult addPaySchool(PaySchool school)throws Exception;

    JsonResult updatePaySchool(PaySchool school)throws Exception;

    JsonResult getPaySchoolIdByNo(String schoolNo);

    JsonResult getPaySchoolQrCodeUrl(Integer schoolId, Integer campusId)throws Exception;

    JsonResult getPaySchoolNoFromAli(Integer schoolId)throws Exception;

    JsonResult getPaySchoolById(Integer schoolId);

    JsonResult getPaySchoolBillsInTime(PaySchool school);

    JsonResult getPaySchoolTotalBillAmount(PaySchool school);

    JsonResult getPaySchoolMonthlyBills(PaySchool school);

    JsonResult getPaySchoolBillsByMonth(PaySchool school);

    JsonResult updateAuthK12(Integer schoolId);
}
