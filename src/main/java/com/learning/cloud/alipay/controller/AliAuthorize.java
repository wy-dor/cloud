package com.learning.cloud.alipay.controller;

import com.learning.domain.JsonResult;
import com.learning.cloud.alipay.service.AlipayService;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AliAuthorize {

    @Value("${ali.key.appId}")
    private String appId;

    @Value("${ali.key.authUrl}")
    private String authUrl;

    @Autowired
    private AlipayService alipayService;

    //生成授权链接
    @GetMapping("aliAuth")
    public JsonResult aliAuth(){
        StringBuilder url = new StringBuilder();
        url.append("https://openauth.alipay.com/oauth2/appToAppAuth.htm?app_id=");
        url.append(appId);
        url.append("&redirect_uri=");
        url.append(authUrl);
        return JsonResultUtil.success(url.toString());
    }

    @GetMapping("aliSandBoxAuth")
    public JsonResult aliSandBoxAuth(){
        StringBuilder url = new StringBuilder();
        url.append("https://openauth.alipaydev.com/oauth2/appToAppAuth.htm?app_id=");
        url.append(appId);
        url.append("&redirect_uri=");
        url.append(authUrl);
        return JsonResultUtil.success(url.toString());
    }

    // 获取授权回调的值app_auth_code换取app_auth_token（应用授权令牌）
    @GetMapping("getAuthToken")
    public JsonResult getAuthToken(String app_auth_code, Integer schoolId)throws Exception{
        return alipayService.getAuthToken(app_auth_code, schoolId);
    }


}
