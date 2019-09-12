package com.learning.cloud.dept.manage.service;

import com.dingtalk.api.response.*;
import com.learning.cloud.user.admin.entity.Administrator;
import com.taobao.api.ApiException;

import java.util.List;
import java.util.Map;

public interface DeptService {
    void init(Integer schoolId) throws ApiException;

    void saveUserInClass(Long deptId) throws ApiException;

    Map<String, String> getUserRole(String userId, String accessToken, String avatar ,String corpId) throws ApiException;

    OapiDepartmentListIdsResponse getDeptListIds(String pDeptId, String accessToken) throws ApiException;

    OapiDepartmentListResponse getDeptList(String pDeptId, String accessToken, Integer isFetchChild) throws ApiException;

    OapiDepartmentGetResponse getDeptDetail(String deptId, String accessToken) throws ApiException;

    OapiUserGetDeptMemberResponse getDeptMember(String deptId, String accessToken) throws ApiException;

    OapiUserSimplelistResponse getDeptUserList(String deptId, String accessToken) throws ApiException;

    OapiUserGetResponse getUserDetail(String userId, String acessToken) throws ApiException;

    OapiUserListbypageResponse getDeptUserListByPage(long deptId, String accessToken) throws ApiException;

    OapiDepartmentListParentDeptsByDeptResponse getListParentDeptsByDept(String deptId, String accessToken) throws ApiException;

    OapiDepartmentListParentDeptsResponse getListParentDeptsByUser(String userId, String accessToken) throws ApiException;

    void userSaveByRole(Integer schoolId, String corpId, Integer campusId, String userId, int roleType, String accessToken) throws ApiException;

    void recurseGetUser(String departmentId, String accessToken, String corpId,Integer schoolId) throws ApiException;

    List<Administrator> getURLAdmin(String accessToken) throws ApiException;

    OapiUserGetOrgUserCountResponse  getOrgUserCount(String accessToken) throws ApiException;
}
