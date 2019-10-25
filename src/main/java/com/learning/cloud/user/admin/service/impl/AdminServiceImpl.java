package com.learning.cloud.user.admin.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetAdminRequest;
import com.dingtalk.api.response.OapiUserGetAdminResponse;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.user.admin.service.AdminService;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019/10/24 3:02 下午
 * @Desc:
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AuthenService authenService;

    @Override
    public String getMainAdmin(String corpId) throws Exception{
        //获取主管理员
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_admin");
        OapiUserGetAdminRequest request = new OapiUserGetAdminRequest();
        request.setHttpMethod("GET");
        OapiUserGetAdminResponse response = client.execute(request, authenService.getAccessToken(corpId));
        String mainUser = "";
        if(response.isSuccess()){
            List<OapiUserGetAdminResponse.AdminList> adminLists = response.getAdminList();
            for(int i=0;i<adminLists.size();i++){
                if(adminLists.get(i).getSysLevel()==1){
                    mainUser = adminLists.get(i).getUserid();
                }
            }
            if(mainUser.isEmpty()){
                return adminLists.get(0).getUserid();
            }else {
                return mainUser;
            }
        }else {
            throw new MyException(JsonResultEnum.DING_SYSADMIN_ERROR);
        }
    }
}
