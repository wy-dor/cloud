package com.learning.cloud.school.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayEcoEduKtSchoolinfoModifyRequest;
import com.alipay.api.response.AlipayEcoEduKtSchoolinfoModifyResponse;
import com.learning.cloud.alipay.AlipayClientUtil;
import com.learning.cloud.bill.entity.AmountItems;
import com.learning.cloud.bill.entity.Bill;
import com.learning.cloud.bizData.dao.SyncBizDataDao;
import com.learning.cloud.bizData.entity.SyncBizData;
import com.learning.cloud.school.dao.PaySchoolDao;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.PaySchool;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.school.service.PaySchoolService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.PayException;
import com.learning.utils.CommonUtils;
import com.learning.utils.DateTransUtil;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class PaySchoolServiceImpl implements PaySchoolService {

    @Autowired
    private PaySchoolDao paySchoolDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private SyncBizDataDao syncBizDataDao;

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
        Integer schoolId = paySchool.getSchoolId();
        School school = schoolDao.getBySchoolId(schoolId);
        if (school == null) {
            return JsonResultUtil.error(0, "不存在该schoolId");
        }
        String corpId = school.getCorpId();
        SyncBizData sbd = new SyncBizData();
        sbd.setCorpId(corpId);
        sbd.setBizType(4);
        List<SyncBizData> byBizData = syncBizDataDao.getByBizData(sbd);
        if (byBizData == null || byBizData.size() == 0) {
            return JsonResultUtil.error(0, "学校尚未授权");
        }
        SyncBizData syncBizData = byBizData.get(0);
        String bizData = syncBizData.getBizData();
        Map<String, Object> parse_0 = (Map<String, Object>) JSON.parse(bizData);
        String syncAction = (String) parse_0.get("syncAction");
        if ("org_suite_auth".equals(syncAction)) {
            Map<String, Object> auth_corp_info = (Map<String, Object>) parse_0.get("auth_corp_info");
            String corp_name = auth_corp_info.get("corp_name").toString();
            String corp_province = auth_corp_info.get("corp_province").toString();
            String corp_city = auth_corp_info.get("corp_city").toString();
            String corp_logo_url = auth_corp_info.get("corp_logo_url").toString();
            paySchool.setSchoolName(corp_name);
            paySchool.setSchoolIcon(corp_logo_url);
            paySchool.setSchoolIconType("jpg");
            paySchool.setProvinceName(corp_province);
            paySchool.setCityName(corp_city);
        }else{
            return JsonResultUtil.error(0,"该校未在授权中");
        }
        int schoolNameExist = paySchoolDao.isPaySchoolNameExist(paySchool.getSchoolName());
        if (schoolNameExist > 0) {
            throw new PayException(JsonResultEnum.SCHOOL_EXIST);
        }
        int i = paySchoolDao.addPaySchool(paySchool);
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
        int i = paySchoolDao.updatePaySchool(school);
        if (i > 0) {
            return JsonResultUtil.success(true);
        } else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult getPaySchoolIdByNo(String schoolNo) {
        Integer schoolId = paySchoolDao.getPaySchoolIdByNo(schoolNo);
        if (schoolId > 0) {
            PaySchool school = paySchoolDao.getPaySchoolBySchoolId(schoolId);
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

    @Override
    public JsonResult getDistrictForPaySchool(Integer schoolId){
        PaySchool paySchool = paySchoolDao.getPaySchoolBySchoolId(schoolId);
        String provinceName = paySchool.getProvinceName();
        String cityName = paySchool.getCityName();
        List<Map<String, String>> areaMapList = new ArrayList<>();
        try {
            ClassPathResource classPathResource = new ClassPathResource("classpath:json/pcd.json");
            String areaData =  IOUtils.toString(classPathResource.getInputStream(), Charset.forName("UTF-8"));
            List<Map<String, Object>> parse = (List<Map<String, Object>>) JSON.parse(areaData);
            for (Map<String, Object> provinceMap : parse) {
                if(provinceMap.get("name").toString().equals(provinceName)){
                    String provinceCode = provinceMap.get("code").toString();
                    paySchool.setProvinceCode(provinceCode);
                    List<Map<String, Object>> cityMapList = (List<Map<String, Object>>)JSON.parse(provinceMap.get("cityList").toString());
                    for (Map<String, Object> cityMap : cityMapList) {
                        if(cityMap.get("name").toString().equals(cityName)){
                            String cityCode = cityMap.get("code").toString();
                            paySchool.setCityCode(cityCode);
                            areaMapList = (List<Map<String, String>>)JSON.parse(provinceMap.get("areaList").toString());
                            break;
                        }
                    }
                    break;
                }
            }
//            JSON.parseObject(classPathResource.getInputStream(), StandardCharsets.UTF_8, List.class,
//                    // 自动关闭流
//                    Feature.AutoCloseSource,
//                    // 允许注释
//                    Feature.AllowComment,
//                    // 允许单引号
//                    Feature.AllowSingleQuotes,
//                    // 使用 Big decimal
//                    Feature.UseBigDecimal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        paySchoolDao.updatePaySchool(paySchool);
        return JsonResultUtil.success(areaMapList);
    }

    //上传学校信息给支付宝，获取支付宝返回的学校编码
    @Override
    public JsonResult getPaySchoolNoFromAli(Integer schoolId) throws Exception {
        PaySchool paySchool = paySchoolDao.getPaySchoolBySchoolId(schoolId);
        String schoolPid = paySchool.getSchoolPid();
        if (schoolPid == null || schoolPid.equals("")) {
            throw new PayException(JsonResultEnum.SCHOOL_PID);
        }
        //todo appAuthToken从何而来
        String appAuthToken = "";
        if (appAuthToken == null || appAuthToken.equals("")) {
            throw new PayException(JsonResultEnum.APP_AUTH_TOKEN);
        }
        String schoolName = paySchool.getSchoolName();
        String schoolType = CommonUtils.schoolTypeFormat(paySchool.getSchoolType());
        String provinceCode = CommonUtils.pcdCodeFormat(paySchool.getProvinceCode());
        String provinceName = paySchool.getProvinceName();
        String cityCode = CommonUtils.pcdCodeFormat(paySchool.getCityCode());
        String cityName = paySchool.getCityName();
        String districtCode = CommonUtils.pcdCodeFormat(paySchool.getDistrictCode());
        String districtName = paySchool.getDistrictName();
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
            int i = paySchoolDao.updatePaySchoolNo(schoolNo, paySchool.getId());
            if (i > 0) {
                return JsonResultUtil.success();
            } else {
                return JsonResultUtil.error(JsonResultEnum.ERROR);
            }
        } else {
            log.error(response.getMsg() + "学校id:" + paySchool.getId() + "的学校获取编码失败！");
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult getPaySchoolById(Integer schoolId) {
        PaySchool school = paySchoolDao.getPaySchoolBySchoolId(schoolId);
        return JsonResultUtil.success(school);
    }

    //获取时间段内支付学校成功的账单
    @Override
    public JsonResult getPaySchoolBillsInTime(PaySchool school) {
        List<Bill> billList = paySchoolDao.getPaySchoolBillsInTime(school);
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
        PaySchool schoolById = paySchoolDao.getPaySchoolBySchoolId(school.getId());
        if (schoolById == null) {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
        AmountItems amountItems = paySchoolDao.getPaySchoolTotalBillAmount(school);
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
        AmountItems amountItems = paySchoolDao.getPaySchoolMonthlyAmount(school);
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
        List<Bill> billList = paySchoolDao.getPaySchoolBillsInTime(school);
        return JsonResultUtil.success(new PageEntity<>(billList));
    }

    //更新school表中auth_K12状态
    @Override
    public JsonResult updateAuthK12(Integer schoolId) {
        Integer i = paySchoolDao.updateAuthK12(schoolId);
        PaySchool school = paySchoolDao.getPaySchoolBySchoolId(schoolId);
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
        if (school != null) {
            amountItems.setSchoolId(school.getId());
            amountItems.setSchoolName(school.getSchoolName());
        }
        return amountItems;
    }

}
