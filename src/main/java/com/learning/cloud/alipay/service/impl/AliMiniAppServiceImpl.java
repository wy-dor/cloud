package com.learning.cloud.alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayOpenAppQrcodeCreateRequest;
import com.alipay.api.response.AlipayOpenAppQrcodeCreateResponse;
import com.learning.cloud.alipay.AlipayClientUtil;
import com.learning.cloud.alipay.entity.QrParam;
import com.learning.cloud.alipay.service.AliMiniAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AliMiniAppServiceImpl implements AliMiniAppService {

    private static String aliMiniUrl;

    @Value("${ali.mini.aliMiniUrl}")
    public void setAliMiniUrl(String aliMiniUrl) {
        AliMiniAppServiceImpl.aliMiniUrl = aliMiniUrl;
    }

    //生产支付宝小程序扫码支付页面二维码
    @Override
    public String getAliMiniAppQrCode(Integer id, Integer campusId) throws Exception{
        String url_param = aliMiniUrl;
        AlipayClient alipayClient = AlipayClientUtil.getMiniClient();
        AlipayOpenAppQrcodeCreateRequest request = new AlipayOpenAppQrcodeCreateRequest();
        QrParam qrParam = new QrParam();
        qrParam.setUrl_param(url_param);
        qrParam.setQuery_param("x="+id+"&y="+campusId);
        qrParam.setDescribe("校收通");
//        String app_auth_token= "201904BB3aa109967a7e41e38993fbf62fa20X63";
//        request.putOtherTextParam("app_auth_token", app_auth_token);
        request.setBizContent(JSON.toJSONString(qrParam));
        log.info(JSON.toJSONString(request));
        AlipayOpenAppQrcodeCreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            log.info("二维码生成，调用成功");
        } else {
            log.error("二维码生成，调用失败");
        }

        return response.getQrCodeUrl();
    }

}
