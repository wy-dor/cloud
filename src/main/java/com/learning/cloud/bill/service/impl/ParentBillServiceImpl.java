package com.learning.cloud.bill.service.impl;

import com.learning.cloud.bill.dao.PreBillDao;
import com.learning.cloud.bill.entity.PreBill;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.enums.JsonResultEnum;
import com.learning.cloud.bill.dao.BillDao;
import com.learning.cloud.bill.dao.ParentBillDao;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bill.entity.ParentBill;
import com.learning.cloud.bill.service.ParentBillService;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ParentBillServiceImpl implements ParentBillService {

    @Autowired
    private ParentBillDao parentBillDao;

    @Autowired
    private BillDao billDao;

//    @Autowired
//    private AlipayService alipayService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private PreBillDao preBillDao;


    @Override
    public JsonResult deleteParentBill(Integer id) throws Exception{
        //只能删除1-未发送，2-子账单空
        ParentBill parentBill = parentBillDao.getParentBillById(id);
        int count = billDao.getCountByParentId(id);
        if(parentBill.getState()==1||count<=0){
            billDao.deleteBillByParentId(id);
            parentBillDao.deleteParentBillById(id);
        }
        return JsonResultUtil.success(true);
    }

    @Override
    public JsonResult getParentBill(ParentBill parentBill) {
        List<ParentBill> parentBills = parentBillDao.getParentBill(parentBill);
        return JsonResultUtil.success(new PageEntity<>(parentBills));
    }

    @Override
    public JsonResult sendBill(Integer id) throws Exception{
        //发送子账单
        List<Bill> bills = billDao.getSchoolBillsByParent(id);
        ParentBill parentBill = parentBillDao.getParentBillById(id);
        Integer schoolId = parentBill.getSchoolId();
        Integer campusId = parentBill.getCampusId();
        for(Bill bill: bills){
            Student student = new Student();
            student.setSchoolId(schoolId);
            student.setCampusId(campusId);
            student.setStudentName(bill.getStudentName());
//            student.setParentPhone(bill.getParentPhone());
//            alipayService.sendAliEduBill(bill, parentBill);
        }
        int i =parentBillDao.updateParent(id);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult addParentBill(ParentBill parentBill) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());
        parentBill.setCreateTime(format);
        int i = parentBillDao.addParentBill(parentBill);
        Integer schoolId = parentBill.getSchoolId();
        Integer parentBillId = parentBill.getId();
        Integer campusId = parentBill.getCampusId();
        Integer classId = parentBill.getClassId();
        GradeClass gc = gradeClassDao.getById(classId);
        String className = gc.getClassName();
        String chargeBillTitle = parentBill.getName();
        String chargeItems = parentBill.getChargeItems();
//        String amountStr = parentBill.getAmountStr();
//        List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(amountStr);
//        for (Map<String, Object> map : parse) {
//            String userId = map.get("userId").toString();
//            BigDecimal amount = new BigDecimal(map.get("amount").toString());
//            PreBill preBill = new PreBill();
//            preBill.setSchoolId(schoolId);
//            preBill.setParentId(parentBillId);
//            preBill.setAmount(amount);
//            preBill.setCampusId(campusId);
//            Student student = studentDao.getByUserId(userId);
//            preBill.setStudentUserId(userId);
//            preBill.setStudentName(student.getStudentName());
//            preBill.setClassId(classId);
//            preBill.setGradeClass(className);
////            preBill.setLastTime();
//            preBill.setChargeBillTitle(chargeBillTitle);
//            preBill.setChargeItems(chargeItems);
//            preBillDao.addPreBill(preBill);
//        }

        BigDecimal amount = parentBill.getAmount();
        String userIdStr = parentBill.getUserIdStr();
        String[] split = userIdStr.split(",");
        for (String userId : split) {
            PreBill preBill = new PreBill();
            preBill.setSchoolId(schoolId);
            preBill.setParentId(parentBillId);
            preBill.setAmount(amount);
            preBill.setCampusId(campusId);
            Student student = studentDao.getByUserId(userId);
            preBill.setStudentUserId(userId);
            preBill.setStudentName(student.getStudentName());
            preBill.setClassId(classId);
            preBill.setGradeClass(className);
            preBill.setChargeBillTitle(chargeBillTitle);
            preBill.setChargeItems(chargeItems);
            preBillDao.addPreBill(preBill);
        }

        return JsonResultUtil.success();
    }

}
