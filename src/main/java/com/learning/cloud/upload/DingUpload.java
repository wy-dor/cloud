package com.learning.cloud.upload;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceGetCustomSpaceRequest;
import com.dingtalk.api.request.OapiCspaceGrantCustomSpaceRequest;
import com.dingtalk.api.request.OapiFileUploadSingleRequest;
import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.dingtalk.api.response.OapiCspaceGrantCustomSpaceResponse;
import com.dingtalk.api.response.OapiFileUploadSingleResponse;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.index.service.CorpAgentService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import com.taobao.api.FileItem;
import com.taobao.api.internal.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyt
 * @Date: 2019-06-25 20:58
 * @Desc:
 */
@RestController
public class DingUpload {

    @Autowired
    private AuthenService authenService;

    @Autowired
    private CorpAgentService corpAgentService;

    //获取企业下的自定义空间
    @GetMapping("/getComSpace")
    public JsonResult getComSpace(String corpId)throws Exception{
        String agentId = corpAgentService.getAgentId(corpId);
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/get_custom_space");
        OapiCspaceGetCustomSpaceRequest request = new OapiCspaceGetCustomSpaceRequest();
        request.setAgentId(agentId);
        request.setDomain("test");
        request.setHttpMethod("GET");
        OapiCspaceGetCustomSpaceResponse response = client.execute(request,accessToken);
        if(response.isSuccess()){
            return JsonResultUtil.success(response.getSpaceid());
        }else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    //授权用户访问企业自定义空间
    @GetMapping("/getComSpaceAccess")
    public JsonResult getComSpaceAccess(String corpId, String userId)throws Exception{
        String agentId = corpAgentService.getAgentId(corpId);
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/grant_custom_space");
        OapiCspaceGrantCustomSpaceRequest request = new OapiCspaceGrantCustomSpaceRequest();
        request.setAgentId(agentId);
        request.setDomain("test");
        request.setType("add");
        request.setUserid(userId);
        request.setPath("/test/");
        request.setDuration(Long.valueOf(3600));
        request.setHttpMethod("GET");
        OapiCspaceGrantCustomSpaceResponse response = client.execute(request,accessToken);
        if(response.isSuccess()){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }

    }

    //单步文件上传
    public String singleUpload(String corpId, String agentId)throws Exception{
        String accessToken = authenService.getAccessToken(corpId);
        OapiFileUploadSingleRequest request = new OapiFileUploadSingleRequest();
        request.setFileSize(1000L);
        request.setAgentId("agentId");
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/file/upload/single?"
                + WebUtils.buildQuery(request.getTextParams(),"utf-8"));
        // 必须重新new一个请求
        request = new OapiFileUploadSingleRequest();
        request.setFile(new FileItem("/Users/mxh/Downloads/test.png"));
        OapiFileUploadSingleResponse response = client.execute(request,accessToken);
        return null;
    }
}
