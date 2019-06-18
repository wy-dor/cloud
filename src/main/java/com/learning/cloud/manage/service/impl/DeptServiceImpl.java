package com.learning.cloud.manage.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.index.dao.AuthCorpInfoDao;
import com.learning.cloud.index.entity.AuthCorpInfo;
import com.learning.cloud.manage.service.DeptService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeptServiceImpl implements DeptService {
    @Autowired
    public AuthCorpInfoDao authCorpInfoDao;

    @Autowired
    public SchoolDao schoolDao;

    @Autowired
    public BureauDao bureaudao;

    /*初始化学校下部门信息*/
    @Override
    public ServiceResult initDepartment() {
        List<AuthCorpInfo> corpInfos = authCorpInfoDao.getCorpInfos();
        for (AuthCorpInfo corpInfo : corpInfos) {
            String corpName = corpInfo.getCorpName();
            if(corpName.endsWith("学校")){
                School bySchoolName = schoolDao.getBySchoolName(corpName);
                if(bySchoolName == null){
                    School school = new School();
                    school.setSchoolName(corpName);
                    schoolDao.insert(school);
                }
            }else{
                Bureau byBureauName = bureaudao.getByBureauName(corpName);
                if(byBureauName == null){
                    Bureau bureau = new Bureau();
                    bureau.setBureauName(corpName);
                    bureaudao.insert(bureau);
                }
            }
        }
        List<School> schools = schoolDao.getSchools();

        //todo
        //填充表
        for (School school : schools) {

        }
        return ServiceResult.success("ok");
    }

    /*获取子部门的id列表*/
    @Override
    public ServiceResult getDeptListIds(String pDeptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId(pDeptId);
        request.setHttpMethod("GET");
        OapiDepartmentListIdsResponse response = client.execute(request, accessToken);
        return ServiceResult.success(response);
    }

    /*获取部门列表*/
    @Override
    public ServiceResult getDeptList(String pDeptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(pDeptId);
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, accessToken);
        return ServiceResult.success(response);
    }

    /*获取部门详情*/
    @Override
    public ServiceResult getDeptDetail(String deptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, accessToken);
        return ServiceResult.success(response);
    }

    /*获取部门用户userid列表*/
    @Override
    public ServiceResult getDeptMember(String deptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
        req.setDeptId(deptId);
        req.setHttpMethod("GET");
        OapiUserGetDeptMemberResponse rsp = client.execute(req, accessToken);
        return ServiceResult.success(rsp);
    }

    /*获取部门用户*/
    @Override
    public ServiceResult getDeptUserList(String deptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(Long.parseLong(deptId));
        request.setOffset(0L);
        request.setSize(10L);
        request.setHttpMethod("GET");
        OapiUserSimplelistResponse response = client.execute(request, accessToken);
        return ServiceResult.success(response);
    }

    /*获取用户详情*/
    @Override
    public ServiceResult getUserDetail(String userId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, accessToken);
        return ServiceResult.success(response);
    }

    /*获取部门用户详情*/
    @Override
    public ServiceResult getDeptUserListByPage(long deptId,String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserListbypageRequest request = new OapiUserListbypageRequest();
        request.setDepartmentId(1L);
        request.setOffset(0L);
        request.setSize(10L);
        request.setOrder("entry_desc");
        request.setHttpMethod("GET");
        OapiUserListbypageResponse execute = client.execute(request,accessToken);
        return ServiceResult.success(execute);
    }

    /*查询部门的所有上级父部门路径*/
    @Override
    public ServiceResult getListParentDeptsByDept(String deptId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts_by_dept");
        OapiDepartmentListParentDeptsByDeptRequest request = new OapiDepartmentListParentDeptsByDeptRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentListParentDeptsByDeptResponse response = client.execute(request, accessToken);
        return ServiceResult.success(response);
    }

    /*查询指定用户的所有上级父部门路径*/
    @Override
    public ServiceResult getListParentDeptsByUser(String userId, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts");
        OapiDepartmentListParentDeptsRequest request = new OapiDepartmentListParentDeptsRequest();
        request.setUserId(userId);
        request.setHttpMethod("GET");
        OapiDepartmentListParentDeptsResponse response = client.execute(request, accessToken);
        return ServiceResult.success(response);
    }
}
