package com.learning.cloud.dept.topApi.controller;

import com.dingtalk.api.response.OapiEduCampusListResponse;
import com.dingtalk.api.response.OapiEduClassListResponse;
import com.dingtalk.api.response.OapiEduGradeListResponse;
import com.dingtalk.api.response.OapiEduPeriodListResponse;
import com.learning.cloud.dept.topApi.service.DeptTopApiService;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptTopApiController {
    @Autowired
    private DeptTopApiService deptTopApiService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private SchoolDao schoolDao;

    @GetMapping("/getTopCampusList")
    public ServiceResult getTopCampusList(String accessToken) throws ApiException{
        return deptTopApiService.getTopCampusList(accessToken);
    }

    @GetMapping("/initTopClassIdInSchool")
    public ServiceResult initTopClassIdInSchool(Integer schoolId) throws ApiException{
//        School bySchoolId = schoolDao.getBySchoolId(schoolId);
//        String corpId = bySchoolId.getCorpId();
//        String accessToken = authenService.getAccessToken(corpId);
        String accessToken = "84afff71b60439b08f54b7bea5bdea3f";
        return deptTopApiService.initTopClassIdInSchool(schoolId, accessToken);
    }

    @GetMapping("/getAndSaveCampusList")
    public ServiceResult getAndSaveCampusList(Integer schoolId) throws ApiException{
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        List<OapiEduCampusListResponse.CampusResponse> campusList = deptTopApiService.getAndSaveCampusList(schoolId, accessToken);
        return ServiceResult.success(campusList);
    }

    @GetMapping("/getProcessInstance")
    public ServiceResult getProcessInstance(Integer schoolId, Long topCampusId) throws ApiException{
        return deptTopApiService.getProcessInstance(schoolId,topCampusId);
    }

    @GetMapping("/getAndSavePeriodList")
    public ServiceResult getAndSavePeriodList(Integer schoolId, Long topCampusId) throws ApiException{
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        List<OapiEduPeriodListResponse.PeriodResponse> periodList = deptTopApiService.getAndSavePeriodList(schoolId, accessToken, topCampusId);
        return ServiceResult.success(periodList);
    }

    @GetMapping("/getAndSaveGradeList")
    public ServiceResult getAndSaveGradeList(Integer schoolId, Long topPeriodId) throws ApiException{
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        List<OapiEduGradeListResponse.GradeResponse> gradeList = deptTopApiService.getAndSaveGradeList(schoolId, accessToken, topPeriodId);
        return ServiceResult.success(gradeList);
    }

    @GetMapping("/getAndSaveClassList")
    public ServiceResult getAndSaveClassList(Integer schoolId, Long topGradeId) throws ApiException{
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        List<OapiEduClassListResponse.ClassResponse> classList = deptTopApiService.getAndSaveClassList(schoolId, accessToken, topGradeId);
        return ServiceResult.success(classList);
    }

    @GetMapping("/getEduClass")
    public ServiceResult getEduClass(Integer schoolId, Long topClassId) throws ApiException{
        return deptTopApiService.getEduClass(schoolId,topClassId);
    }

}
