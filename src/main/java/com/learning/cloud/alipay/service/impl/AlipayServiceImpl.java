package com.learning.cloud.alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayEcoEduKtBillingSendRequest;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayEcoEduKtBillingSendResponse;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiEduStudentGetRequest;
import com.dingtalk.api.response.OapiEduStudentGetResponse;
import com.learning.cloud.alipay.service.AlipaySignService;
import com.learning.cloud.bill.dao.PreBillDao;
import com.learning.cloud.bill.entity.PreBill;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.PaySchoolDao;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.PaySchool;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.parent.dao.ParentPhoneDao;
import com.learning.cloud.user.parent.entity.ParentPhone;
import com.learning.domain.JsonResult;
import com.learning.cloud.alipay.AlipayClientUtil;
import com.learning.cloud.alipay.entity.BillParam;
import com.learning.cloud.alipay.entity.ChargeItems;
import com.learning.cloud.alipay.entity.UserDetails;
import com.learning.cloud.alipay.service.AlipayService;
import com.learning.enums.JsonResultEnum;
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
    private SchoolDao schoolDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Autowired
    private PreBillDao preBillDao;

    @Autowired
    private ParentPhoneDao parentPhoneDao;

    @Autowired
    private AlipaySignService alipaySignService;

    @Value("${ali.key.appId}")
    private String appId;

    //发送教育缴费账单接口
    @Override
    public JsonResult sendAliEduBill(Integer preId) throws Exception {
        PreBill preBill = preBillDao.getPreBillById(preId);
        JsonResult jsonResult = null;
        //根据schoolId获取学校信息，(前端实现兼容老款二维码，使用schoolNo获取学校信息)
        PaySchool paySchool = paySchoolDao.getPaySchoolBySchoolId(preBill.getSchoolId());
        String appAuthToken = paySchool.getAppAuthToken();
        String childName = preBill.getStudentName();
        Long deptId = new Long("0");
        String grade = "";
        String classIn = preBill.getGradeClass();
        Integer classId = preBill.getClassId();
        if (classId != null) {
            GradeClass gc = gradeClassDao.getById(preBill.getClassId());
            grade = gc.getGradeName();
            classIn = gc.getClassName();
            deptId = gc.getDeptId();
        }
        //生成缴费账单编号
        String isvTradeNo = CommonUtils.getIsvTradeNo();
        //生成当面付账单名称
        String chargeBillTitle = preBill.getChargeBillTitle();
        //当面付账单缴费项不可选
        String chargeType = "M";
        List<ChargeItems> chargeItems = JSON.parseArray(preBill.getChargeItems(), ChargeItems.class);
        //缴费截止时间,当面付默认截止时间为当前时间后24小时内支付
        String gmtEnd = CommonUtils.getFaceBillHoursLater();
        //Y为gmt_end生效，用户过期后，不能再缴费。
        String endEnable = "Y";
        BillParam billParam = new BillParam();
        List<UserDetails> users = new ArrayList<>();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/student/get");
        OapiEduStudentGetRequest req = new OapiEduStudentGetRequest();
        req.setClassId(deptId);
        req.setStudentUserid(preBill.getStudentUserId());
        School school = schoolDao.getBySchoolId(preBill.getSchoolId());
        String corpId = school.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        OapiEduStudentGetResponse rsp = client.execute(req, accessToken);
        OapiEduStudentGetResponse.StudentRespone result = rsp.getResult();
        List<OapiEduStudentGetResponse.GuardianRespone> guardians = result.getGuardians();
        for (OapiEduStudentGetResponse.GuardianRespone guardian : guardians) {
            UserDetails userDetails = new UserDetails();
            String guardianUserId = guardian.getGuardianUserid();
            ParentPhone parentPhone = parentPhoneDao.getByUserId(guardianUserId);
            if(parentPhone == null){
                return JsonResultUtil.error(JsonResultEnum.NO_PHONE_INFO);
            }
            userDetails.setUser_mobile(parentPhone.getPhone());
            userDetails.setUser_name(guardian.getNick());
            users.add(userDetails);
        }
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
        billParam.setAmount(preBill.getAmount());
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
        request.setBizContent(JSON.toJSONString(billParam));


        AlipayEcoEduKtBillingSendResponse response = alipayClient.execute(request);
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

        return null;
    }

    @Override
    public JsonResult testSendAliEduBill() throws Exception {
        JsonResult jsonResult = null;
        //根据schoolId获取学校信息，(前端实现兼容老款二维码，使用schoolNo获取学校信息)
        PaySchool paySchool = paySchoolDao.getPaySchoolBySchoolId(1);

        String childName = "小明";
        String grade = "一年级";
        String classIn = "一班";

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
        billParam.setAmount(new BigDecimal("0.01"));
        billParam.setGmt_end(gmtEnd);
        billParam.setEnd_enable(endEnable);
        //Isv支付宝pid
        billParam.setPartner_id(pid);
        //组装完成，调用ali sdk

        String info = alipaySignService.getSignedOrder(billParam);

        return JsonResultUtil.success(info);

    }

    @Override
    public JsonResult testSendAliEduBillOrder() throws Exception {
        JsonResult jsonResult = null;
        //根据schoolId获取学校信息，(前端实现兼容老款二维码，使用schoolNo获取学校信息)
        PaySchool paySchool = paySchoolDao.getPaySchoolBySchoolId(1);
        String appAuthToken = "201904BB369b995471e847b78ccec413acd29X39";
        String childName = "于柠萌";
        String grade = "一年级";
        String classIn = "一班";

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
        userDetails.setUser_mobile("15651999726");
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
        billParam.setAmount(new BigDecimal("0.01"));
        billParam.setGmt_end(gmtEnd);
        billParam.setEnd_enable(endEnable);
        //Isv支付宝pid
        billParam.setPartner_id(pid);
        //组装完成，调用ali sdk
        AlipayClient alipayClient = AlipayClientUtil.getClient(appId);
        //发送缴费账单接口
        AlipayEcoEduKtBillingSendRequest request = new AlipayEcoEduKtBillingSendRequest();
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent(JSON.toJSONString(billParam));
        AlipayEcoEduKtBillingSendResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            log.info(response.getMsg());
            //账单发送成功，获取返回值，更新bill
            //支付宝－中小学－教育缴费的账单号 order_no

            return JsonResultUtil.success(response.getOrderNo());

        }else {
            //更新状态
            log.error(response.getMsg());
            return null;
        }

    }

    // 授权回调令牌
    @Override
    public JsonResult getAuthToken(String app_auth_code, Integer schoolId) throws Exception{
        AlipayClient alipayClient = AlipayClientUtil.getClient();
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent("{" +
                "    \"grant_type\":\"authorization_code\"," +
                "    \"code\":\""+app_auth_code+"\"" +
                "}");
        AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            String appAuthToken = response.getAppAuthToken();
            PaySchool paySchool = new PaySchool();
            paySchool.setSchoolId(schoolId);
            paySchool.setAppAuthToken(appAuthToken);
            int i = paySchoolDao.updatePaySchool(paySchool);
            if(i>0){
                return JsonResultUtil.success();
            }else{
                return JsonResultUtil.error(JsonResultEnum.ERROR);
            }
        }else {
            log.error(response.getMsg()+"schoolId:"+schoolId+"授权失败");
            return JsonResultUtil.error(0, response.getSubMsg());
        }
    }

}
