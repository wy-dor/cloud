package com.learning.cloud.user.admin.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRoleListRequest;
import com.dingtalk.api.request.OapiRoleSimplelistRequest;
import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiRoleSimplelistResponse;
import com.learning.cloud.user.admin.service.AdministratorService;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {

    /*获取所有的角色列表*/
    public OapiRoleListResponse getRoleList(String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/list");
        OapiRoleListRequest request = new OapiRoleListRequest();
        request.setOffset(0L);
        request.setSize(10L);
        OapiRoleListResponse response = client.execute(request, accessToken);
        return response;
    }

    public ServiceResult getRoleSimpleList(long roleId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/simplelist");
        OapiRoleSimplelistRequest request = new OapiRoleSimplelistRequest();
        request.setRoleId(roleId);
        request.setOffset(0L);
        request.setSize(10L);
        OapiRoleSimplelistResponse response = client.execute(request, accessToken);
        return ServiceResult.success(response);
    }


}
