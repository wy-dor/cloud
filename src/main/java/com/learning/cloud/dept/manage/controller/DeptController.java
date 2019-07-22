package com.learning.cloud.dept.manage.controller;

import com.learning.cloud.dept.manage.service.DeptService;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.school.service.SchoolService;
import com.learning.cloud.term.service.TermService;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private TermService termService;

    /*关联school与bureau*/
    /*传入id(school的id)和bureauId*/
    @GetMapping("/api/setBureauId")
    public ServiceResult setBureauId(School school) throws ApiException {
        Integer i = schoolService.setBureauId(school);
        try {
            termService.getSchoolTerm(school.getId().longValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(i > 0){
            deptService.init(school.getId());
        }else{
            return ServiceResult.success("已加入");
        }
        return ServiceResult.success("设置成功");
    }

    /*学校结构数据同步*/
    @PostMapping("/api/initSchool")
    public ServiceResult initSchool(Integer schoolId) throws ApiException {
        deptService.init(schoolId);
        return ServiceResult.success("同步成功");
    }

    /*班级结构数据同步*/
    @GetMapping("/api/updateUserInClass")
    public ServiceResult updateUserInClass(int classId) throws ApiException{
        deptService.saveUserInClass(classId);
        return ServiceResult.success("更新成功");
    }

    /*获取部门列表*/
    @GetMapping("/api/getDeptList")
    public ServiceResult getDeptList(String pDeptId, String accessToken, Integer isFetchChild) throws ApiException {
        return ServiceResult.success(deptService.getDeptList(pDeptId,accessToken,isFetchChild));
    }

    /*获取部门详情*/
    @GetMapping("/api/getDeptDetail")
    public ServiceResult getDeptDetail(String deptId, String accessToken) throws ApiException {
        return ServiceResult.success(deptService.getDeptDetail(deptId,accessToken));
    }

    /*获取部门用户userid列表*/
    @GetMapping("/api/getDeptMember")
    public ServiceResult getDeptMember(String deptId, String accessToken) throws ApiException {
        return ServiceResult.success(deptService.getDeptMember(deptId,accessToken));
    }

    /*获取部门用户*/
    @GetMapping("/api/getDeptUserList")
    public ServiceResult getDeptUserList(String deptId, String accessToken) throws ApiException {
        return ServiceResult.success(deptService.getDeptUserList(deptId,accessToken));
    }

    /*获取用户详情*/
    @GetMapping("/api/getUserDetail")
    public ServiceResult getUserDetail(String userId, String corpId) throws ApiException {
        return ServiceResult.success(deptService.getUserDetail(userId,corpId));
    }

    /*获取部门用户详情*/
    @GetMapping("/api/getDeptUserListByPage")
    public ServiceResult getDeptUserListByPage(long deptId, String accessToken) throws ApiException {
        return ServiceResult.success(deptService.getDeptUserListByPage(deptId,accessToken));
    }

    /*获取子部门id列表*/
    @GetMapping("/api/getDeptListIds")
    public ServiceResult getDeptListIds(String pDeptId, String accessToken) throws ApiException {
        return ServiceResult.success(ServiceResult.success(deptService.getDeptListIds(pDeptId,accessToken)));
    }

    /*查询部门的所有上级父部门路径*/
    @GetMapping("/api/getListParentDepts")
    public ServiceResult getListParentDepts(String deptId, String accessToken) throws ApiException {
        return ServiceResult.success(deptService.getListParentDeptsByDept(deptId,accessToken));
    }

    /*查询指定用户的所有上级父部门路径*/
    @GetMapping("/api/getListParentDeptsByUser")
    public ServiceResult getListParentDeptsByUser(String userId, String accessToken) throws ApiException {
        return ServiceResult.success(deptService.getListParentDeptsByUser(userId,accessToken));
    }

}
