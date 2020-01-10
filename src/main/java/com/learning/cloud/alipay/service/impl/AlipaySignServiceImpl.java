package com.learning.cloud.alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayEcoEduKtBillingSendRequest;
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

    private static String appPrivateKey;

    @Value("${ali.key.private}")
    public void setAppPrivateKey(String appPrivateKey) {
        AlipaySignServiceImpl.appPrivateKey = appPrivateKey;
    }

    @Override
    public String getSignedOrder(AlipayEcoEduKtBillingSendRequest request)throws Exception{

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data=new Date(System.currentTimeMillis());
        String timestamp = formatter.format(data);
        String notify_url = "http://114.55.168.33:80/payment_notify";
        String app_id =  "2019080666140167";
        String method = "alipay.eco.edu.kt.billing.send";
        String format = "json";
        String charset = "utf-8";
        String sign_type = "RSA2";
        String version = "1.0";

        String biz_content = JSON.toJSONString(request);

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("app_id",app_id);
        paramMap.put("method",method);
        paramMap.put("charset",charset);
        paramMap.put("sign_type",sign_type);
        paramMap.put("notify_url",notify_url);
        paramMap.put("timestamp",timestamp);
        paramMap.put("biz_content",biz_content);
        paramMap.put("version",version);

        String signOrderUrl = getSignContent(paramMap);
        String rsaSign = "";
        /**
         @param content 加签内容
         @param privateKey 加签私钥
         @param charset 加签字符集
         @param charset 签名方法
         **/
        try {
            rsaSign= AlipaySignature.rsaSign(signOrderUrl, appPrivateKey, "utf-8",sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        Map<String,String> retMap = new HashMap<>();
        retMap.put("app_id", URLEncoder.encode(app_id,"UTF-8"));
        retMap.put("method",URLEncoder.encode(method,"UTF-8"));
        retMap.put("charset",URLEncoder.encode(charset,"UTF-8"));
        retMap.put("sign_type",URLEncoder.encode(sign_type,"UTF-8"));
        retMap.put("notify_url",URLEncoder.encode(notify_url,"UTF-8"));
        retMap.put("timestamp",URLEncoder.encode(timestamp,"UTF-8"));
        retMap.put("biz_content",URLEncoder.encode(biz_content,"UTF-8"));
        retMap.put("version",URLEncoder.encode(version,"UTF-8"));
        String retSignOrderUrl = getSignContent(retMap);
        retSignOrderUrl = retSignOrderUrl +"&sign="+URLEncoder.encode(rsaSign, "UTF-8");
        return retSignOrderUrl;


    }

    public String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for(int i = 0; i < keys.size(); ++i) {
            String key = (String)keys.get(i);
            String value = (String)sortedParams.get(key);
            if (com.alipay.api.internal.util.StringUtils.areNotEmpty(new String[]{key, value})) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                ++index;
            }
        }

        return content.toString();
    }

}
