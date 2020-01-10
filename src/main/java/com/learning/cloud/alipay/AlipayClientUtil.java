package com.learning.cloud.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: yyt
 * @Date: 2019-01-02 17:11
 * @Desc: alipayclient实例
 */
@Component
public class AlipayClientUtil {
    //支付宝网关
    private static final String URL = "https://openapi.alipay.com/gateway.do";
    private static final String CHARSET = "utf-8";

    private static String appId;

    private static String appPrivateKey;

    private static String alipayPublicKey;

    private static String miniAppId;

    @Value("${ali.key.appId}")
    public void setAppId(String appId) {
        AlipayClientUtil.appId = appId;
    }

    @Value("${ali.key.private}")
    public void setAppPrivateKey(String appPrivateKey) {
        AlipayClientUtil.appPrivateKey = appPrivateKey;
    }

    @Value("${ali.key.public}")
    public void setAlipayPublicKey(String alipayPublicKey) {
        AlipayClientUtil.alipayPublicKey = alipayPublicKey;
    }

    @Value("${ali.key.miniAppId}")
    public void setMiniAppId(String miniAppId) {
        AlipayClientUtil.miniAppId = miniAppId;
    }

    public static AlipayClient getClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(
                URL,appId,appPrivateKey,"json",CHARSET, alipayPublicKey, "RSA2"
        );
        return alipayClient;
    }

    public static AlipayClient getClient(String appId){
        AlipayClient alipayClient = new DefaultAlipayClient(
                URL,appId,appPrivateKey,"json",CHARSET, alipayPublicKey, "RSA2"
        );
        return alipayClient;
    }

    public static AlipayClient getMiniClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(
                URL,miniAppId,appPrivateKey,"json",CHARSET, alipayPublicKey, "RSA2"
        );
        return alipayClient;
    }
}
