package com.learning.cloud.dept.manage.service;

import com.dingtalk.api.response.*;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface DeptService {

    ServiceResult initDepartment();

    OapiDepartmentListIdsResponse getDeptListIds(String pDeptId, String accessToken);

    OapiDepartmentListResponse getDeptList(String pDeptId, String accessToken, Integer isFetchChild);

    OapiDepartmentGetResponse getDeptDetail(String deptId, String accessToken);

    OapiUserGetDeptMemberResponse getDeptMember(String deptId, String accessToken);

    OapiUserSimplelistResponse getDeptUserList(String deptId, String accessToken);

    OapiUserGetResponse getUserDetail(String userId, String accessToken);

    OapiUserListbypageResponse getDeptUserListByPage(long deptId, String accessToken);

    OapiDepartmentListParentDeptsByDeptResponse getListParentDeptsByDept(String deptId, String accessToken);

    OapiDepartmentListParentDeptsResponse getListParentDeptsByUser(String userId, String accessToken);

    OapiRoleListResponse getRoleList(String accessToken);
}
