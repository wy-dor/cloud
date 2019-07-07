package com.learning.cloud.innerAppTest.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.innerAppTest.config.InnerAppContanst;
import com.learning.cloud.innerAppTest.service.InnerAppService;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InnerAppServiceImpl implements InnerAppService {

    @Autowired
    private DeptService deptService;

    public ServiceResult getSchoolDeptIds() throws ApiException {
        Map<String,String> map = new HashMap<>();
        String accessToken = getToken();
        OapiDepartmentListResponse resp = deptService.getDeptList("1", accessToken, 1);
        List<OapiDepartmentListResponse.Department> deptList = resp.getDepartment();
        for (OapiDepartmentListResponse.Department dept : deptList) {
            //递归得到学校部门id
            if(dept.getSourceIdentifier() != null){
                String deptIdStr = dept.getParentid().toString();
                getSchoolDepts(map, deptList, deptIdStr);
            }
        }
        return ServiceResult.success(map);
    }

    private void getSchoolDepts(Map<String,String> map, List<OapiDepartmentListResponse.Department> deptList, String deptIdStr) {
        for (OapiDepartmentListResponse.Department department : deptList) {
            if(department.getId().toString().equals(deptIdStr)){
                if(department.getSourceIdentifier() == null){
                    map.put(deptIdStr,department.getName());
                }else{
                    deptIdStr = department.getParentid().toString();
                    getSchoolDepts(map,deptList,deptIdStr);
                }
            }
        }
    }

    public String getToken() throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(InnerAppContanst.APPKEY);
        request.setAppsecret(InnerAppContanst.APPSECRET);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }
}
