package com.learning.cloud.upload;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceGetCustomSpaceRequest;
import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.learning.cloud.index.service.AuthenService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: yyt
 * @Date: 2019-06-25 20:58
 * @Desc:
 */
@Component
public class DingUpload {

    @Autowired
    private AuthenService authenService;

    @GetMapping("getComSpace")
    public JsonResult getComSpace(String corpId, String agentId)throws Exception{
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/get_custom_space");
        OapiCspaceGetCustomSpaceRequest request = new OapiCspaceGetCustomSpaceRequest();
        request.setAgentId(agentId);
        request.setDomain("test");
        request.setHttpMethod("GET");
        OapiCspaceGetCustomSpaceResponse response = client.execute(request,accessToken);
        String spaceId = response.getSpaceid();
        return JsonResultUtil.success(spaceId);
    }

}
