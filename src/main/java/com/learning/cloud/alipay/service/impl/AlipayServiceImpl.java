package com.learning.cloud.alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayEcoEduKtBillingSendRequest;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.school.dao.PaySchoolDao;
import com.learning.cloud.school.entity.PaySchool;
import com.learning.domain.JsonResult;
import com.learning.cloud.alipay.AlipayClientUtil;
import com.learning.cloud.alipay.entity.BillParam;
import com.learning.cloud.alipay.entity.ChargeItems;
import com.learning.cloud.alipay.entity.UserDetails;
import com.learning.cloud.alipay.service.AlipayService;
import com.learning.cloud.bill.dao.BillDao;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bill.entity.ParentBill;
import com.learning.utils.CommonUtils;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AlipayServiceImpl implements AlipayService {

    @Value("${ali.isv.pid}")
    private String pid;

    @Autowired
    private PaySchoolDao paySchoolDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Value("${ali.key.appId}")
    private String appId;

    //发送教育缴费账单接口
    @Override
    public JsonResult sendAliEduBill(Bill bill, ParentBill parentBill) throws Exception {
        JsonResult jsonResult = null;
        //根据schoolId获取学校信息，(前端实现兼容老款二维码，使用schoolNo获取学校信息)
        PaySchool paySchool = paySchoolDao.getPaySchoolById(bill.getSchoolId());
        String appAuthToken = "201904BB369b995471e847b78ccec413acd29X39";
        String childName = bill.getStudentName();
        String grade = "";
        String classIn = bill.getGradeClass();
        Integer classId = bill.getClassId();
        if (classId != null) {
            GradeClass gc = gradeClassDao.getById(bill.getClassId());
            grade = gc.getGradeName();
            classIn = gc.getClassName();
        }
        //生成缴费账单编号
        String isvTradeNo = CommonUtils.getIsvTradeNo();
        //生成当面付账单名称
        String chargeBillTitle = parentBill.getName();
        //当面付账单缴费项不可选
        String chargeType = "M";
        List<ChargeItems> chargeItems = JSON.parseArray(bill.getChargeItem(), ChargeItems.class);
        //缴费截止时间,当面付默认截止时间为当前时间后24小时内支付
        String gmtEnd = CommonUtils.getFaceBillHoursLater();
        //Y为gmt_end生效，用户过期后，不能再缴费。
        String endEnable = "Y";
        BillParam billParam = new BillParam();
        List<UserDetails> users = new ArrayList<>();
        UserDetails userDetails = new UserDetails();
        userDetails.setUser_mobile(bill.getParentPhone());
        users.add(userDetails);
        //家长信息
        billParam.setUsers(users);
        //学校支付宝pid
        billParam.setSchool_pid(paySchool.getSchoolPid());
        //学校编码
        billParam.setSchool_no(paySchool.getSchoolNo());
        //孩子名字
        billParam.setChild_name(childName);
        //年级
        billParam.setGrade(grade);
        //班级
        billParam.setClass_in(classIn);
        billParam.setOut_trade_no(isvTradeNo);
        billParam.setCharge_bill_title(chargeBillTitle);
        billParam.setCharge_type(chargeType);
        //缴费详情
        billParam.setCharge_item(chargeItems);
        //总金额
        billParam.setAmount(bill.getAmount());
        billParam.setGmt_end(gmtEnd);
        billParam.setEnd_enable(endEnable);
        //Isv支付宝pid
        billParam.setPartner_id(pid);
        //组装完成，调用ali sdk
        AlipayClient alipayClient = AlipayClientUtil.getClient(appId);
        //发送缴费账单接口
        AlipayEcoEduKtBillingSendRequest request = new AlipayEcoEduKtBillingSendRequest();
        log.info(JSON.toJSONString(request));
        request.putOtherTextParam("app_auth_token", appAuthToken);
//        request.setBizContent(JSON.toJSONString(billParam));

        return JsonResultUtil.success(JSON.toJSONString(billParam));
//        Date sendTime = new Date();
//        AlipayEcoEduKtBillingSendResponse response = alipayClient.execute(request);
//        if(response.isSuccess()){
//            log.info(response.getMsg());
//            //账单发送成功，获取返回值，更新bill
//            //支付宝－中小学－教育缴费的账单号 order_no
//            billDao.updateBillById(response.getOrderNo(),sendTime,bill.getId(), isvTradeNo);
//
//        }else {
//            //更新状态
//            log.error(response.getMsg());
//            billDao.updateBillStateById(PayConfig.FAILURE,bill.getId());
//            throw new PayException(JsonResultEnum.BILL_SEND_FAILURE);
//        }

    }

    @Override
    public JsonResult testSendAliEduBill() throws Exception {
        JsonResult jsonResult = null;
        //根据schoolId获取学校信息，(前端实现兼容老款二维码，使用schoolNo获取学校信息)
        PaySchool paySchool = paySchoolDao.getPaySchoolById(1);
        String appAuthToken = "201904BB369b995471e847b78ccec413acd29X39";
        String childName = "小明";
        String grade = "";
        String classIn = "一年级一班";
        Integer classId = 57;
        if (classId != null) {
            GradeClass gc = gradeClassDao.getById(57);
            grade = gc.getGradeName();
            classIn = gc.getClassName();
        }
        //生成缴费账单编号
        String isvTradeNo = CommonUtils.getIsvTradeNo();
        //生成当面付账单名称
        String chargeBillTitle = "测试缴费";
        //当面付账单缴费项不可选
        String chargeType = "M";
        List<ChargeItems> chargeItems = JSON.parseArray("", ChargeItems.class);
        //缴费截止时间,当面付默认截止时间为当前时间后24小时内支付
        String gmtEnd = CommonUtils.getFaceBillHoursLater();
        //Y为gmt_end生效，用户过期后，不能再缴费。
        String endEnable = "Y";
        BillParam billParam = new BillParam();
        List<UserDetails> users = new ArrayList<>();
        UserDetails userDetails = new UserDetails();
        userDetails.setUser_mobile("13112345678");
        users.add(userDetails);
        //家长信息
        billParam.setUsers(users);
        //学校支付宝pid
        billParam.setSchool_pid(paySchool.getSchoolPid());
        //学校编码
        billParam.setSchool_no(paySchool.getSchoolNo());
        //孩子名字
        billParam.setChild_name(childName);
        //年级
        billParam.setGrade(grade);
        //班级
        billParam.setClass_in(classIn);
        billParam.setOut_trade_no(isvTradeNo);
        billParam.setCharge_bill_title(chargeBillTitle);
        billParam.setCharge_type(chargeType);
        //缴费详情
        billParam.setCharge_item(chargeItems);
        //总金额
        billParam.setAmount(new BigDecimal("200.00"));
        billParam.setGmt_end(gmtEnd);
        billParam.setEnd_enable(endEnable);
        //Isv支付宝pid
        billParam.setPartner_id(pid);
        //组装完成，调用ali sdk
        AlipayClient alipayClient = AlipayClientUtil.getClient(appId);
        //发送缴费账单接口
        AlipayEcoEduKtBillingSendRequest request = new AlipayEcoEduKtBillingSendRequest();
        log.info(JSON.toJSONString(request));
        request.putOtherTextParam("app_auth_token", appAuthToken);
//        request.setBizContent(JSON.toJSONString(billParam));

        return JsonResultUtil.success(JSON.toJSONString(billParam));
//        Date sendTime = new Date();
//        AlipayEcoEduKtBillingSendResponse response = alipayClient.execute(request);
//        if(response.isSuccess()){
//            log.info(response.getMsg());
//            //账单发送成功，获取返回值，更新bill
//            //支付宝－中小学－教育缴费的账单号 order_no
//            billDao.updateBillById(response.getOrderNo(),sendTime,bill.getId(), isvTradeNo);
//
//        }else {
//            //更新状态
//            log.error(response.getMsg());
//            billDao.updateBillStateById(PayConfig.FAILURE,bill.getId());
//            throw new PayException(JsonResultEnum.BILL_SEND_FAILURE);
//        }

    }

}
