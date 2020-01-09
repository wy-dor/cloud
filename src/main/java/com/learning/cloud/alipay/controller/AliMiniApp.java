package com.learning.cloud.alipay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.internal.util.AlipaySignature;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 支付宝小程序
 */
@Slf4j
@RestController
public class AliMiniApp {

    //生产支付宝小程序扫码支付页面二维码

    //解密
    @PostMapping("decryptor")
    public JsonResult decryptor(@RequestBody String encryptContent)throws Exception{
        String response = encryptContent;
        Map<String, String> openapiResult = JSON.parseObject(response,
                new TypeReference<Map<String, String>>() {
                }, Feature.OrderedField);
        String signType = StringUtils.defaultIfBlank(openapiResult.get("sign_type"), "RSA2");
        String charset = StringUtils.defaultIfBlank(openapiResult.get("charset"), "UTF-8");
        String encryptType = StringUtils.defaultIfBlank(openapiResult.get("encrypt_type"), "AES");
        String sign = openapiResult.get("sign");
        //"SG7BgrQHzMd2i2KbPodM8TtrvYqQvX40K5d35BVqK5iYOPdtMJwxDIZbMPJgkMI9ONKu3dxa5hGPWhZAEE3AbnSiVffUyCg2+uytDXjoPeuESTtJLRYghKoyxQ4kFwnwpMW6qd3iiPKlobzWYM+dSi1ZEGK2pSvqDCoK07EdlxkhATc/VZuS9jqT7USeJW9BffH7RPmpe55FG775H0vHI5HuTAOlnhHDhnCO+D+AR19Snasvr7IfUGi3vE0z29FmTw7iqpNIHbl7MFsZY2c6jZdirmDf3RNivjUqL7V4dcmfdMv8Xd7XJ0pzOOOujCfZA7guxx7duYVqyJH9i2kqjQ==";
        String content = openapiResult.get("response");
        //"MocLiICSP29smQ5/tdPmXYi9hOe5Zm84/A7WnS01aFMBRwSekQbOxNtMJLa+UPA6cZXZoxBUZKtF9eUfzSY4bw==";

        //如果密文的
        boolean isDataEncrypted = !content.startsWith("{");
        boolean signCheckPass;

        //2. 验签
        String signContent = content;
        //支付宝应用公钥
        String signVeriKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiacZtNfG+u1iyXRBmPfXNn/SfmylpefBX0B+VOvFdVLB78C1Tbste3gS/FL2MejxFAVzUj3nrCyV82nyHBUktnSbFzz9K+VTrwipG51JBcwqosUf+ZW3MQgNdV8aEhZyp97uiBlYJFdnjNuvLcbRedJondUJtvp5r3fAS2MIDJxOSXi6EPHvm0HSC5sR/A4dHt5bo2uUaGiG4CLnMsaJgnftOKo9IZY/jFmt70ucIJo5wo4LKjP0l3U3jL0pOQe9v95q52KN128DLfpu3zcbZNuATbNvvUxCXao1WKRpE3NzLkdilRYLnk07nZWyOnfXI1yxefvE1UNl31ZMK+5YbwIDAQAB";

        //如果是加密的报文则需要在密文的前后添加双引号
        if (isDataEncrypted) {
            signContent = "\"" + signContent + "\"";
        }
        try {
            signCheckPass = AlipaySignature.rsaCheck(signContent, sign, signVeriKey, charset, signType);
        } catch (AlipayApiException e) {
            //验签异常, 日志
            log.error("验签异常，encryptContent=" + encryptContent, e);
            return JsonResultUtil.error(0001,"验签异常");
        }

        if (!signCheckPass) {
            //验签不通过（异常或者报文被篡改），终止流程（不需要做解密）
            log.error("验签失败，encryptContent=" + encryptContent);
            return JsonResultUtil.error(0001,"验签失败");
        }

        //支付宝小程序AES秘钥
        String encryptKey = "4D2msF/l3cc7gf5ApRlFnA==";

        //3. 解密
        String plainData;
        if (isDataEncrypted) {
            try {
                plainData = AlipayEncrypt.decryptContent(content, encryptType, encryptKey, charset);
            } catch (AlipayApiException e) {
                //解密异常, 日志
                log.error("解密异常，encryptContent=" + encryptContent, e);
                return JsonResultUtil.error(0001,"解密异常");
            }
        } else {
            plainData = content;
        }
        return JsonResultUtil.success(plainData);
    }


}
