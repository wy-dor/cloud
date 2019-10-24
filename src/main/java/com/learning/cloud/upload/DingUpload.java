package com.learning.cloud.upload;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceGetCustomSpaceRequest;
import com.dingtalk.api.request.OapiCspaceGrantCustomSpaceRequest;
import com.dingtalk.api.request.OapiFileUploadSingleRequest;
import com.dingtalk.api.request.OapiProcessinstanceCspaceInfoRequest;
import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.dingtalk.api.response.OapiCspaceGrantCustomSpaceResponse;
import com.dingtalk.api.response.OapiFileUploadSingleResponse;
import com.dingtalk.api.response.OapiProcessinstanceCspaceInfoResponse;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.index.service.CorpAgentService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.MyException;
import com.learning.utils.JsonResultUtil;
import com.taobao.api.FileItem;
import com.taobao.api.internal.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @Autowired
    private CorpAgentDao corpAgentDao;

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

    /**
     * 获取审批钉盘空间信息接口，需结合需前端API“上传附件到钉盘”使用，使用方法如下：
     *
     * （1）调用获取审批钉盘空间信息”接口，获取钉盘控件的上传权限，并获取space_id
     *
     * （2）使用参数space_id，调用“H5微应用-JSAPI-上传附件到钉盘”或“小程序-上传附件到钉盘”后，获取钉盘附件的信息
     *
     * （3）调用发起审批实例接口，传递附件信息
     */
    public Long getApprovingDingSpace(String corpId,String userId)throws Exception{
        CorpAgent corpAgent = corpAgentDao.getByCorpId(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/cspace/info");
        OapiProcessinstanceCspaceInfoRequest req = new OapiProcessinstanceCspaceInfoRequest();
        req.setUserId("manager3214");
        req.setAgentId(Long.valueOf(corpAgent.getAgentId()));
        OapiProcessinstanceCspaceInfoResponse response = client.execute(req, authenService.getAccessToken(corpId));
        if(response.isSuccess()){
            OapiProcessinstanceCspaceInfoResponse.AppSpaceResponse asp = response.getResult();
            return asp.getSpaceId();
        }else {
            throw new MyException(JsonResultEnum.DING_SPACE_ERROR);
        }
    }
}
