package com.learning.cloud.index.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiSsoGettokenRequest;
import com.dingtalk.api.request.OapiSsoGetuserinfoRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSsoGettokenResponse;
import com.dingtalk.api.response.OapiSsoGetuserinfoResponse;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.PayException;
import com.learning.utils.JsonResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 免登
 */
@RestController
public class LoginController {

    //应用管理后台免登
    @GetMapping("oaLogin")
    public JsonResult oaLogin(String code)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sso/gettoken");
        OapiSsoGettokenRequest request = new OapiSsoGettokenRequest();
        request.setCorpid("dingc5fa8a42960b8d6d35c2f4657eb6378f");
        request.setCorpsecret("alPGrVK4bsLEJFS2CxAHHD7Th9HlVvhhCfcbTVIdibFMRmaJ8dpcjGuRC6Olah4R");
        request.setHttpMethod("GET");
        OapiSsoGettokenResponse response = client.execute(request);
        if(response.isSuccess()){
            String accessToken = response.getAccessToken();
            JsonResult jsonResult = getAdministrator(code, accessToken);
            return JsonResultUtil.success(jsonResult);
        }else {
            return JsonResultUtil.error(JsonResultEnum.OA_LOGIN_ERROR);
        }
    }

    private JsonResult getAdministrator(String code, String accessToken) throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sso/getuserinfo");
        OapiSsoGetuserinfoRequest request = new OapiSsoGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");
        OapiSsoGetuserinfoResponse response = client.execute(request,accessToken);
        if(response.isSuccess()){
            if(response.getIsSys()){
                return JsonResultUtil.success(response);
            }else{
                throw new PayException(JsonResultEnum.OA_LOGIN_NOT_SYS);
            }
        }else {
            throw new PayException(JsonResultEnum.OA_LOGIN_ERROR);
        }
    }


    @GetMapping("getUserInfoByCode")
    public JsonResult getUserInfoByCode(String code)throws Exception{
        DefaultDingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(code);
        OapiSnsGetuserinfoBycodeResponse response = client.execute(req,"dingoa751853q8xqbn9tlp","2R7Wpl7TXy0WR64osQXlPmosBbLVzTjM6e0nGKXcGO-NbMo4_EfWJg4i0EQ5BM-X");
        if(response.isSuccess()){
            return JsonResultUtil.success(response);
        }else {
            return JsonResultUtil.error(JsonResultEnum.THIRD_LOGIN_ERROR);
        }
    }

}
