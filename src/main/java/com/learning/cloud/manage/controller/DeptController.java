package com.learning.cloud.manage.controller;

import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.manage.service.DeptService;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

    @Autowired
    public DeptService deptService;

    @Autowired
    public AuthenService authenService;

    @GetMapping("/initDepartment")
    public ServiceResult initDepartment(){
        return deptService.initDepartment();
    }

    /*获取部门列表*/
    @GetMapping("/getDeptList")
    public ServiceResult getDeptList(String pDeptId,String accessToken) throws ApiException {
        return deptService.getDeptList(pDeptId,accessToken);
    }

    /*获取部门详情*/
    @GetMapping("/getDeptDetail")
    public ServiceResult getDeptDetail(String deptId,String accessToken) throws ApiException {
        return deptService.getDeptDetail(deptId,accessToken);
    }

    /*获取部门用户userid列表*/
    @GetMapping("/getDeptMember")
    public ServiceResult getDeptMember(String deptId,String accessToken) throws ApiException {
        return deptService.getDeptMember(deptId,accessToken);
    }

    /*获取部门用户*/
    @GetMapping("/getDeptUserList")
    public ServiceResult getDeptUserList(String deptId,String accessToken) throws ApiException {
        return deptService.getDeptUserList(deptId,accessToken);
    }

    /*获取用户详情*/
    @GetMapping("/getUserDetail")
    public ServiceResult getUserDetail(String userId,String accessToken) throws ApiException {
        return deptService.getUserDetail(userId,accessToken);
    }

    /*获取部门用户详情*/
    @GetMapping("/getDeptUserListByPage")
    public ServiceResult getDeptUserListByPage(long deptId,String accessToken) throws ApiException {
        return deptService.getDeptUserListByPage(deptId,accessToken);
    }

    /*获取子部门id列表*/
    @GetMapping("/getDeptListIds")
    public ServiceResult getDeptListIds(String pDeptId,String accessToken) throws ApiException {
        return deptService.getDeptListIds(pDeptId,accessToken);
    }

    /*查询部门的所有上级父部门路径*/
    @GetMapping("/getListParentDepts")
    public ServiceResult getListParentDepts(String deptId,String accessToken) throws ApiException {
        return deptService.getListParentDeptsByDept(deptId,accessToken);
    }

    /*查询指定用户的所有上级父部门路径*/
    @GetMapping("/getListParentDeptsByUser")
    public ServiceResult getListParentDeptsByUser(String userId, String accessToken) throws ApiException {
        return deptService.getListParentDeptsByUser(userId,accessToken);
    }
}
