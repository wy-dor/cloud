package com.learning.cloud.school.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayEcoEduKtSchoolinfoModifyRequest;
import com.alipay.api.response.AlipayEcoEduKtSchoolinfoModifyResponse;
import com.learning.cloud.alipay.AlipayClientUtil;
import com.learning.cloud.bill.entity.AmountItems;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.school.dao.PaySchoolDao;
import com.learning.cloud.school.entity.PaySchool;
import com.learning.cloud.school.service.PaySchoolService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.PayException;
import com.learning.utils.CommonUtils;
import com.learning.utils.DateTransUtil;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class PaySchoolServiceImpl implements PaySchoolService {

    @Autowired
    private PaySchoolDao schoolDao;

    private static String pid;

    private static String name;

    private static String phone;

    private static String notifyUrl;

    private static String psd;

    private static String webUrl;

    @Value("${ali.isv.pid}")
    public void setPid(String pid) {
        PaySchoolServiceImpl.pid = pid;
    }

    @Value("${ali.isv.name}")
    public void setName(String name) {
        PaySchoolServiceImpl.name = name;
    }

    @Value("${ali.isv.phone}")
    public void setPhone(String phone) {
        PaySchoolServiceImpl.phone = phone;
    }

    @Value("${ali.isv.notifyUrl}")
    public void setNotifyUrl(String notifyUrl) {
        PaySchoolServiceImpl.notifyUrl = notifyUrl;
    }

    @Value("${user.psd}")
    public void setPsd(String psd) {
        this.psd = psd;
    }

    @Value("${sys.webUrl}")
    public void setWebUrl(String webUrl) {
        PaySchoolServiceImpl.webUrl = webUrl;
    }

    @Value("${ali.key.appId}")
    private String appId;

    //新增学校
    @Transactional
    @Override
    public JsonResult addPaySchool(PaySchool paySchool) throws Exception {
            int schoolNameExist = schoolDao.isPaySchoolNameExist(paySchool.getSchoolName());
            if (schoolNameExist > 0) {
                throw new PayException(JsonResultEnum.SCHOOL_EXIST);
            }
            int i = schoolDao.addPaySchool(paySchool);
            if (i > 0) {
                return JsonResultUtil.success(paySchool.getId());
            } else {
                return JsonResultUtil.error(JsonResultEnum.ERROR);
            }
    }

    //更新学校
    @Override
    public JsonResult updatePaySchool(PaySchool school) throws Exception {
        //只能更新动态学校
        if (school.getSchoolIcon() != null && school.getSchoolIconType() == null) {
            throw new PayException(JsonResultEnum.SCHOOL_ICON);
        }
        int i = schoolDao.updatePaySchool(school);
        if (i > 0) {
            return JsonResultUtil.success(true);
        } else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult getPaySchoolIdByNo(String schoolNo) {
        Integer schoolId = schoolDao.getPaySchoolIdByNo(schoolNo);
        if (schoolId > 0) {
            PaySchool school = schoolDao.getPaySchoolById(schoolId);
            return JsonResultUtil.success(school);
        } else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    //获取学校的二维码地址
    @Override
    public JsonResult getPaySchoolQrCodeUrl(Integer schoolId, Integer campusId) throws Exception {
        if (schoolId == null) {
            throw new PayException(JsonResultEnum.SCHOOL_ID_REQUIRED);
        }

        //String url = aliMiniAppService.getAliMiniAppQrCode(schoolId, campusId);
        //以上方法为小程序使用生成小程序二维码特定界面，由于小程序不支持支付跳转废弃，直接返回服务器页面前端拼接F
        return JsonResultUtil.success(webUrl);
    }

    //上传学校信息给支付宝，获取支付宝返回的学校编码
    @Override
    public JsonResult getPaySchoolNoFromAli(Integer schoolId) throws Exception {
        PaySchool school = schoolDao.getPaySchoolById(schoolId);
        String schoolPid = school.getSchoolPid();
        if (schoolPid == null || schoolPid.equals("")) {
            throw new PayException(JsonResultEnum.SCHOOL_PID);
        }
        String appAuthToken = "";
        if (appAuthToken == null || appAuthToken.equals("")) {
            throw new PayException(JsonResultEnum.APP_AUTH_TOKEN);
        }
        String schoolName = school.getSchoolName();
        String schoolType = CommonUtils.schoolTypeFormat(school.getSchoolType());
        String provinceCode = CommonUtils.pcdCodeFormat(school.getProvinceCode());
        String provinceName = school.getProvinceName();
        String cityCode = CommonUtils.pcdCodeFormat(school.getCityCode());
        String cityName = school.getCityName();
        String districtCode = CommonUtils.pcdCodeFormat(school.getDistrictCode());
        String districtName = school.getDistrictName();
        AlipayClient alipayClient = AlipayClientUtil.getClient();
        alipayClient = AlipayClientUtil.getClient(appId);
        AlipayEcoEduKtSchoolinfoModifyRequest request = new AlipayEcoEduKtSchoolinfoModifyRequest();
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent("{\n" +
                "  \"school_name\":\"" + schoolName + "\",\n" +
                "  \"school_type\":\"" + schoolType + "\",\n" +
                "  \"province_code\":\"" + provinceCode + "\",\n" +
                "  \"province_name\":\"" + provinceName + "\",\n" +
                "  \"city_code\":\"" + cityCode + "\",\n" +
                "  \"city_name\":\"" + cityName + "\",\n" +
                "  \"district_code\":\"" + districtCode + "\",\n" +
                "  \"district_name\":\"" + districtName + "\",\n" +
                "  \"isv_name\":\"" + name + "\",\n" +
                "  \"isv_notify_url\":\"" + notifyUrl + "\",\n" +
                "  \"isv_pid\":\"" + pid + "\",\n" +
                "  \"school_pid\":\"" + schoolPid + "\",\n" +
                "  \"isv_phone\":\"" + phone + "\",\n" +
                "}");//设置业务参数
        AlipayEcoEduKtSchoolinfoModifyResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            String schoolNo = response.getSchoolNo();
            //更新user表
            int i = schoolDao.updatePaySchoolNo(schoolNo, school.getId());
            if (i > 0) {
                return JsonResultUtil.success();
            } else {
                return JsonResultUtil.error(JsonResultEnum.ERROR);
            }
        } else {
            log.error(response.getMsg() + "学校id:" + school.getId() + "的学校获取编码失败！");
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult getPaySchoolById(Integer schoolId) {
        PaySchool school = schoolDao.getPaySchoolById(schoolId);
        return JsonResultUtil.success(school);
    }

    //获取时间段内支付学校成功的账单
    @Override
    public JsonResult getPaySchoolBillsInTime(PaySchool school) {
        List<Bill> billList = schoolDao.getPaySchoolBillsInTime(school);
        if (null == billList) {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        } else if (billList.size() == 0) {
            return JsonResultUtil.error(JsonResultEnum.BILLS_IN_TIME_ZERO);
        }
        return JsonResultUtil.success(new PageEntity<>(billList));
    }

    //获取学校下的账单统计
    @Override
    public JsonResult getPaySchoolTotalBillAmount(PaySchool school) {
        PaySchool schoolById = schoolDao.getPaySchoolById(school.getId());
        if(schoolById == null){
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
        AmountItems amountItems = schoolDao.getPaySchoolTotalBillAmount(school);
        if (amountItems == null) {
            amountItems = initAmountItems(schoolById);
        }
        return JsonResultUtil.success(amountItems);
    }

    //获取指定月份下的学校账单统计
    @Override
    public JsonResult getPaySchoolMonthlyBills(PaySchool school) {
        Map<String, String> stringMap = DateTransUtil.monthToDateRange(school.getMonth());
        school.setStartTime(stringMap.get("startTime"));
        school.setEndTime(stringMap.get("endTime"));
        AmountItems amountItems = schoolDao.getPaySchoolMonthlyAmount(school);
        if (amountItems.getTotalAmount() == null) {
            amountItems = initAmountItems(school);
        }
        return JsonResultUtil.success(amountItems);
    }

    //获取指定月份下的学校账单
    @Override
    public JsonResult getPaySchoolBillsByMonth(PaySchool school) {
        Map<String, String> stringMap = DateTransUtil.monthToDateRange(school.getMonth());
        school.setStartTime(stringMap.get("startTime"));
        school.setEndTime(stringMap.get("endTime"));
        List<Bill> billList = schoolDao.getPaySchoolBillsInTime(school);
        return JsonResultUtil.success(new PageEntity<>(billList));
    }

    //更新school表中auth_K12状态
    @Override
    public JsonResult updateAuthK12(Integer schoolId) {
        Integer i = schoolDao.updateAuthK12(schoolId);
        PaySchool school = schoolDao.getPaySchoolById(schoolId);
        if (i > 0) {
            return JsonResultUtil.success(school);
        } else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    private AmountItems initAmountItems(PaySchool school) {
        AmountItems amountItems;
        amountItems = new AmountItems();
        amountItems.setTotalAmount(new BigDecimal("0"));
        amountItems.setBillCount(new Integer(0));
        if(school != null){
            amountItems.setSchoolId(school.getId());
            amountItems.setSchoolName(school.getSchoolName());
        }
        return amountItems;
    }

}
