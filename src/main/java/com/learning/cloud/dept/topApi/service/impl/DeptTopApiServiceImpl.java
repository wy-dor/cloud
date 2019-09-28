package com.learning.cloud.dept.topApi.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.learning.cloud.dept.campus.dao.CampusDao;
import com.learning.cloud.dept.campus.entity.Campus;
import com.learning.cloud.dept.gradeClass.dao.GradeClassDao;
import com.learning.cloud.dept.gradeClass.entity.GradeClass;
import com.learning.cloud.dept.topApi.dao.EduGradeDao;
import com.learning.cloud.dept.topApi.dao.EduPeriodDao;
import com.learning.cloud.dept.topApi.entity.EduGrade;
import com.learning.cloud.dept.topApi.entity.EduPeriod;
import com.learning.cloud.dept.topApi.service.DeptTopApiService;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptTopApiServiceImpl implements DeptTopApiService {
    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private CampusDao campusDao;

    @Autowired
    private EduPeriodDao eduPeriodDao;

    @Autowired
    private EduGradeDao eduGradeDao;

    @Autowired
    private GradeClassDao gradeClassDao;

    @Override
    public ServiceResult getAndSaveCampusList(Integer schoolId) throws ApiException {
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/campus/list");
        OapiEduCampusListRequest req = new OapiEduCampusListRequest();
        OapiEduCampusListResponse rsp = client.execute(req, accessToken);
        List<OapiEduCampusListResponse.CampusResponse> result = rsp.getResult();
        int size = result.size();
        for (OapiEduCampusListResponse.CampusResponse campusResponse : result) {
            Long topCampusId = campusResponse.getCampusId();
            String name = campusResponse.getName();
            Campus campus = new Campus();
            campus.setSchoolId(schoolId);
            campus.setCampusName(name);
            Campus c = campusDao.getByCampus(campus);
            campus.setTopCampusId(topCampusId);
            if(size > 1){
                campus.setState(1);
            }
            if (c == null) {
                campusDao.insert(campus);
            } else {
                int campusId = c.getId();
                campus.setId(campusId);
                campusDao.update(campus);
            }

        }
        return ServiceResult.success(rsp);
    }

    @Override
    public ServiceResult getProcessInstance(Integer schoolId, Long topCampusId) throws ApiException {
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
        OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
        request.setProcessInstanceId(topCampusId.toString());
        OapiProcessinstanceGetResponse response = client.execute(request,accessToken);
        return ServiceResult.success(response);
    }

    @Override
    public ServiceResult getAndSavePeriodList(Integer schoolId, Long topCampusId) throws ApiException {
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/period/list");
        OapiEduPeriodListRequest req = new OapiEduPeriodListRequest();
        req.setCampusId(topCampusId);
        OapiEduPeriodListResponse rsp = client.execute(req, accessToken);
        List<OapiEduPeriodListResponse.PeriodResponse> result = rsp.getResult();
        for (OapiEduPeriodListResponse.PeriodResponse periodResponse : result) {
            String name = periodResponse.getName();
            Long periodId = periodResponse.getPeriodId();
            String periodType = periodResponse.getPeriodType();
            EduPeriod eduPeriod = new EduPeriod();
            eduPeriod.setTopPeriodId(periodId);
            List<EduPeriod> byPeriod = eduPeriodDao.getByPeriod(eduPeriod);
            eduPeriod.setSchoolId(schoolId);
            eduPeriod.setPeriodName(name);
            eduPeriod.setPeriodType(periodType);
            eduPeriod.setTopCampusId(topCampusId);
            if(byPeriod != null && byPeriod.size() > 0){
                eduPeriodDao.update(eduPeriod);
            }else{
                eduPeriodDao.insert(eduPeriod);
            }
        }
        return ServiceResult.success(rsp);
    }

    @Override
    public ServiceResult getAndSaveGradeList(Integer schoolId, Long topPeriodId) throws ApiException {
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/grade/list");
        OapiEduGradeListRequest req = new OapiEduGradeListRequest();
        req.setPeriodId(topPeriodId);
        OapiEduGradeListResponse rsp = client.execute(req, accessToken);
        List<OapiEduGradeListResponse.GradeResponse> result = rsp.getResult();
        for (OapiEduGradeListResponse.GradeResponse gradeResponse : result) {
            Long gradeLevel = gradeResponse.getGradeLevel();
            Long campusId = gradeResponse.getCampusId();
            Long gradeId = gradeResponse.getGradeId();
            String startYear = gradeResponse.getStartYear();
            String name = gradeResponse.getName();
            EduGrade eduGrade = new EduGrade();
            eduGrade.setTopGradeId(gradeId);
            List<EduGrade> byGrade = eduGradeDao.getByGrade(eduGrade);
            eduGrade.setSchoolId(schoolId);
            eduGrade.setGradeName(name);
            eduGrade.setTopCampusId(campusId);
            eduGrade.setTopPeriodId(topPeriodId);
            eduGrade.setGradeLevel(gradeLevel);
            eduGrade.setStartYear(startYear);
            if(byGrade != null && byGrade.size() > 0){
                eduGradeDao.update(eduGrade);
            }else{
                eduGradeDao.insert(eduGrade);
            }
        }
        return ServiceResult.success(rsp);
    }

    @Override
    public ServiceResult getAndSaveClassList(Integer schoolId, Long topGradeId) throws ApiException {
        School bySchoolId = schoolDao.getBySchoolId(schoolId);
        String corpId = bySchoolId.getCorpId();
        String accessToken = authenService.getAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/edu/class/list");
        OapiEduClassListRequest req = new OapiEduClassListRequest();
        req.setGradeId(topGradeId);
        Boolean flag = true;
        Long pageNo = 1L;
        List<OapiEduClassListResponse.ClassResponse> allList = null;
        while(flag){
            req.setPageNo(pageNo);
            req.setPageSize(10L);
            OapiEduClassListResponse rsp = client.execute(req, accessToken);
            OapiEduClassListResponse.PageResult result = rsp.getResult();
            List<OapiEduClassListResponse.ClassResponse> list = result.getList();
            allList.addAll(list);
            for (OapiEduClassListResponse.ClassResponse classResponse : list) {
                Long topCampusId = classResponse.getCampusId();
                Long classId = classResponse.getClassId();
                Long periodId = classResponse.getPeriodId();
                Long gradeId = classResponse.getGradeId();
                String name = classResponse.getName();
                Campus campus = new Campus();
                campus.setTopCampusId(topCampusId);
                Campus byCampus = campusDao.getByCampus(campus);
                GradeClass gradeClass = new GradeClass();
                gradeClass.setSchoolId(schoolId);
                gradeClass.setCampusId(byCampus.getId());
                gradeClass.setClassName(name);
                List<GradeClass> byGradeClass = gradeClassDao.getByGradeClass(gradeClass);
                gradeClass.setTopClassId(classId);
                gradeClass.setTopGradeId(gradeId);
                gradeClass.setTopPeriodId(periodId);
                gradeClass.setTopCampusId(topCampusId);
                if(byGradeClass != null && byGradeClass.size() > 0){
                    Integer id = byGradeClass.get(0).getId();
                    gradeClass.setId(id);
                    gradeClassDao.update(gradeClass);
                }else{
                    gradeClassDao.insert(gradeClass);
                }
            }
            //是否继续查询
            if(result.getHasMore()){
                pageNo += 1;
            }else{
                flag = false;
            }

        }

        return ServiceResult.success(allList);
    }


}
