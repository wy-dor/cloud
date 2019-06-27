package com.learning.cloud.dept.manage.service;

import com.dingtalk.api.response.*;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface DeptService {
    void init(School school) throws ApiException;

    String getUserRole(String userId, String accessToken) throws ApiException;

    OapiDepartmentListIdsResponse getDeptListIds(String pDeptId, String accessToken) throws ApiException;

    OapiDepartmentListResponse getDeptList(String pDeptId, String accessToken, Integer isFetchChild) throws ApiException;

    OapiDepartmentGetResponse getDeptDetail(String deptId, String accessToken) throws ApiException;

    OapiUserGetDeptMemberResponse getDeptMember(String deptId, String accessToken) throws ApiException;

    OapiUserSimplelistResponse getDeptUserList(String deptId, String accessToken) throws ApiException;

    OapiUserGetResponse getUserDetail(String userId, String corpId) throws ApiException;

    OapiUserListbypageResponse getDeptUserListByPage(long deptId, String accessToken) throws ApiException;

    OapiDepartmentListParentDeptsByDeptResponse getListParentDeptsByDept(String deptId, String accessToken) throws ApiException;

    OapiDepartmentListParentDeptsResponse getListParentDeptsByUser(String userId, String accessToken) throws ApiException;

}
