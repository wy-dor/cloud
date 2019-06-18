package com.learning.cloud.manage.service;

import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface DeptService {

    ServiceResult initDepartment();

    ServiceResult getDeptListIds(String pDeptId,String accessToken) throws ApiException;

    ServiceResult getDeptList(String pDeptId,String accessToken) throws ApiException;

    ServiceResult getDeptDetail(String deptId,String accessToken) throws ApiException;

    ServiceResult getDeptMember(String deptId,String accessToken) throws ApiException;

    ServiceResult getDeptUserList(String deptId,String accessToken) throws ApiException;

    ServiceResult getUserDetail(String userId,String accessToken) throws ApiException;

    ServiceResult getDeptUserListByPage(long deptId,String accessToken) throws ApiException;

    ServiceResult getListParentDeptsByDept(String deptId, String accessToken) throws ApiException;

    ServiceResult getListParentDeptsByUser(String userId, String accessToken) throws ApiException;
}
