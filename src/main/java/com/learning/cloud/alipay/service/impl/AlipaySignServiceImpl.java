package com.learning.cloud.alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayEcoEduKtBillingSendRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.learning.cloud.alipay.AlipayClientUtil;
import com.learning.cloud.alipay.entity.BillParam;
import com.learning.cloud.alipay.service.AlipaySignService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.taobao.api.internal.toplink.endpoint.MessageType.ValueFormat.Date;

/**
 * @Author: yyt
 * @Date: 2020/1/10 2:17 下午
 * @Desc:
 */
@Service
public class AlipaySignServiceImpl implements AlipaySignService {

    //支付宝网关
    private static final String URL = "https://openapi.alipay.com/gateway.do";
    private static final String CHARSET = "utf-8";

    private static String appId;

    private static String appPrivateKey;

    private static String alipayPublicKey;

    private static String miniAppId;

    @Value("${ali.key.appId}")
    public void setAppId(String appId) {
        AlipaySignServiceImpl.appId = appId;
    }

    @Value("${ali.key.private}")
    public void setAppPrivateKey(String appPrivateKey) {
        AlipaySignServiceImpl.appPrivateKey = appPrivateKey;
    }

    @Value("${ali.key.public}")
    public void setAlipayPublicKey(String alipayPublicKey) {
        AlipaySignServiceImpl.alipayPublicKey = alipayPublicKey;
    }

    @Value("${ali.key.miniAppId}")
    public void setMiniAppId(String miniAppId) {
        AlipaySignServiceImpl.miniAppId = miniAppId;
    }

    AlipayClient alipayClient = new DefaultAlipayClient(URL, appId, appPrivateKey, "json", CHARSET, alipayPublicKey,"RSA2");
    AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

    @Override
    public String getSignedOrder(BillParam billParam)throws Exception{
        String appAuthToken = "201904BB369b995471e847b78ccec413acd29X39";
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent(JSON.toJSONString(billParam));
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response.getBody();
    }



}
