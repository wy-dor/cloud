package com.learning.cloud.index.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiSsoGettokenRequest;
import com.dingtalk.api.request.OapiSsoGetuserinfoRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSsoGettokenResponse;
import com.dingtalk.api.response.OapiSsoGetuserinfoResponse;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.bureau.service.BureauService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.PayException;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 免登
 */
@RestController
public class LoginController {

    @Autowired
    private SchoolDao schoolDao;

    //应用管理后台免登
    @GetMapping("/oaLogin")
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
                School byCorpId = schoolDao.getSchoolByCorpId(response.getCorpInfo().getCorpid());
                Map<String, Object> result = new HashMap<>();
                if(byCorpId == null){

                    result.put("isSchool",false);
                    result.put("body",response.getBody());
                }else {
                    result.put("isSchool",true);
                    result.put("body",response.getBody());
                }
                return JsonResultUtil.success(result);
            }else {
                return JsonResultUtil.error(JsonResultEnum.OA_LOGIN_NOT_SYS);
            }
        }else {
            return JsonResultUtil.error(JsonResultEnum.OA_LOGIN_ERROR);
        }
    }


    @GetMapping("/getUserInfoByCode")
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
