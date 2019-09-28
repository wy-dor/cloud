package com.learning.cloud.dept.topApi.controller;

import com.learning.cloud.dept.topApi.service.DeptTopApiService;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptTopApiController {
    @Autowired
    private DeptTopApiService deptTopApiService;

    @GetMapping("/getAndSaveCampusList")
    public ServiceResult getAndSaveCampusList(Integer schoolId) throws ApiException{
        return deptTopApiService.getAndSaveCampusList(schoolId);
    }

    @GetMapping("/getProcessInstance")
    public ServiceResult getProcessInstance(Integer schoolId, Long topCampusId) throws ApiException{
        return deptTopApiService.getProcessInstance(schoolId,topCampusId);
    }

    @GetMapping("/getAndSavePeriodList")
    public ServiceResult getAndSavePeriodList(Integer schoolId, Long topCampusId) throws ApiException{
        return deptTopApiService.getAndSavePeriodList(schoolId,topCampusId);
    }

    @GetMapping("/getAndSaveGradeList")
    public ServiceResult getAndSaveGradeList(Integer schoolId, Long topPeriodId) throws ApiException{
        return deptTopApiService.getAndSaveGradeList(schoolId, topPeriodId);
    }

    @GetMapping("/getAndSaveClassList")
    public ServiceResult getAndSaveClassList(Integer schoolId, Long topGradeId) throws ApiException{
        return deptTopApiService.getAndSaveClassList(schoolId, topGradeId);
    }

    @GetMapping("/getEduClass")
    public ServiceResult getEduClass(Integer schoolId, Long topClassId) throws ApiException{
        return deptTopApiService.getEduClass(schoolId,topClassId);
    }

}
