package com.learning.cloud.workProcess.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessSaveRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessSaveResponse;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.workProcess.dao.ProcessDao;
import com.learning.cloud.workProcess.dao.ProcessInstanceDao;
import com.learning.cloud.workProcess.entity.Attachment;
import com.learning.cloud.workProcess.entity.CourseInstance;
import com.learning.cloud.workProcess.entity.Process;
import com.learning.cloud.workProcess.entity.ProcessInstance;
import com.learning.cloud.workProcess.service.CourseExchangeProcessService;
import com.learning.cloud.workProcess.service.ProcessService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.learning.cloud.dingCommon.DingUtils.getUserByUserid;

/**
 * @Author: yyt
 * @Date: 2019/11/14 11:24 上午
 * @Desc:
 */
@Transactional
@Service
public class CourseExchangeProcessServiceImpl implements CourseExchangeProcessService {

    @Autowired
    private ProcessDao processDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private CorpAgentDao corpAgentDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessInstanceDao processInstanceDao;

    private static String PROCESS_SAVE = "https://oapi.dingtalk.com/topapi/process/save";

    private static String PROCESS_INSTANCE_CREATE = "https://oapi.dingtalk.com/topapi/processinstance/create";

    @Override
    public JsonResult createCourseExchangeProcess(String corpId) throws Exception {
        //判断模板是否已经创建
        Process pro = processDao.getCourseProcess(corpId);
        if(pro!=null){
            return JsonResultUtil.success("流程模板已经创建");
        }
        // 根据corpId获取学校信息
        School school = schoolDao.getSchoolByCorpId(corpId);
        CorpAgent corpAgent = corpAgentDao.getByCorpId(corpId);

        DingTalkClient client = new DefaultDingTalkClient(PROCESS_SAVE);

        OapiProcessSaveRequest request = new OapiProcessSaveRequest();
        OapiProcessSaveRequest.SaveProcessRequest saveProcessRequest = new OapiProcessSaveRequest.SaveProcessRequest();
        saveProcessRequest.setDisableFormEdit(true);
        saveProcessRequest.setName("调课申请");
//        saveProcessRequest.setProcessCode("PROC-89DFDDB4-9F2C-4E5F-B5FE-1496B8742CB8");
        saveProcessRequest.setAgentid(Long.valueOf(corpAgent.getAgentId()));

        // 注意，每种表单组件，对应的componentName是固定的，参照一下示例代码
        List<OapiProcessSaveRequest.FormComponentVo> formComponentList = Lists.newArrayList();

        // 2班 2019-11-15 第三节 语文课 申请和 2019-11-15 第四节 数学课调换
        //1.班级
        OapiProcessSaveRequest.FormComponentVo singleInput = new OapiProcessSaveRequest.FormComponentVo();
        singleInput.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo singleInputProp = new OapiProcessSaveRequest.FormComponentPropVo();
        singleInputProp.setRequired(true);
        singleInputProp.setLabel("班级");
        singleInputProp.setPlaceholder("请输入");
        singleInputProp.setId("TextField-J78F050R");
        singleInput.setProps(singleInputProp);
        formComponentList.add(singleInput);

        //2.日期
        OapiProcessSaveRequest.FormComponentVo dateComponent = new OapiProcessSaveRequest.FormComponentVo();
        dateComponent.setComponentName("DDDateField");
        OapiProcessSaveRequest.FormComponentPropVo dateComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        dateComponentProp.setRequired(false);
        dateComponentProp.setLabel("日期");
        dateComponentProp.setPlaceholder("请选择");
        dateComponentProp.setUnit("天"); // 小时或天
        dateComponentProp.setId("DDDateField-J0MTJZVE");
        dateComponent.setProps(dateComponentProp);
        formComponentList.add(dateComponent);

        //3.节次
        OapiProcessSaveRequest.FormComponentVo singleInput1 = new OapiProcessSaveRequest.FormComponentVo();
        singleInput1.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo singleInputProp1 = new OapiProcessSaveRequest.FormComponentPropVo();
        singleInputProp1.setRequired(true);
        singleInputProp1.setLabel("节次");
        singleInputProp1.setPlaceholder("请输入");
        singleInputProp1.setId("TextField-J78F051R");
        singleInput1.setProps(singleInputProp1);
        formComponentList.add(singleInput1);

        //4.原有课程名称
        OapiProcessSaveRequest.FormComponentVo singleInput2 = new OapiProcessSaveRequest.FormComponentVo();
        singleInput2.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo singleInputProp2 = new OapiProcessSaveRequest.FormComponentPropVo();
        singleInputProp2.setRequired(true);
        singleInputProp2.setLabel("课程");
        singleInputProp2.setPlaceholder("请输入");
        singleInputProp2.setId("TextField-J78F052R");
        singleInput2.setProps(singleInputProp2);
        formComponentList.add(singleInput2);

        //5.日期
        OapiProcessSaveRequest.FormComponentVo dateComponent1 = new OapiProcessSaveRequest.FormComponentVo();
        dateComponent1.setComponentName("DDDateField");
        OapiProcessSaveRequest.FormComponentPropVo dateComponentProp1 = new OapiProcessSaveRequest.FormComponentPropVo();
        dateComponentProp1.setRequired(false);
        dateComponentProp1.setLabel("调换日期");
        dateComponentProp1.setPlaceholder("请选择");
        dateComponentProp1.setUnit("天"); // 小时或天
        dateComponentProp1.setId("DDDateField-J1MTJZVE");
        dateComponent1.setProps(dateComponentProp1);
        formComponentList.add(dateComponent1);

        //6.节次
        OapiProcessSaveRequest.FormComponentVo singleInput3 = new OapiProcessSaveRequest.FormComponentVo();
        singleInput3.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo singleInputProp3 = new OapiProcessSaveRequest.FormComponentPropVo();
        singleInputProp3.setRequired(true);
        singleInputProp3.setLabel("调换节次");
        singleInputProp3.setPlaceholder("请输入");
        singleInputProp3.setId("TextField-J78F053R");
        singleInput3.setProps(singleInputProp3);
        formComponentList.add(singleInput3);

        //7.被调换的课程名称
        OapiProcessSaveRequest.FormComponentVo singleInput4 = new OapiProcessSaveRequest.FormComponentVo();
        singleInput4.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo singleInputProp4 = new OapiProcessSaveRequest.FormComponentPropVo();
        singleInputProp4.setRequired(true);
        singleInputProp4.setLabel("调换课程");
        singleInputProp4.setPlaceholder("请输入");
        singleInputProp4.setId("TextField-J78F054R");
        singleInput4.setProps(singleInputProp4);
        formComponentList.add(singleInput4);



        //8.申请理由
        OapiProcessSaveRequest.FormComponentVo multipleInput = new OapiProcessSaveRequest.FormComponentVo();
        multipleInput.setComponentName("TextareaField");
        OapiProcessSaveRequest.FormComponentPropVo multipleInputProp = new OapiProcessSaveRequest.FormComponentPropVo();
        multipleInputProp.setRequired(true);
        multipleInputProp.setLabel("申请理由");
        multipleInputProp.setPlaceholder("请输入");
        multipleInputProp.setId("TextareaField-J78F056S");
        multipleInput.setProps(multipleInputProp);
        formComponentList.add(multipleInput);

        saveProcessRequest.setFormComponentList(formComponentList);
        request.setSaveProcessRequest(saveProcessRequest);

        //保存流程信息
        Process process = new Process();
        String params = JSON.toJSONString(formComponentList);
        process.setAgentId(corpAgent.getAgentId());
        process.setCorpId(corpId);
        process.setFormComponentList(params);
        process.setDisableFormEdit("true");
        process.setName("调课申请");
        process.setDescription("调课申请模板");
        processDao.insert(process);

        OapiProcessSaveResponse response = client.execute(request, authenService.getAccessToken(corpId));
        //保存流程创建模板
        if(response.isSuccess()){
            OapiProcessSaveResponse.ProcessTopVo v = response.getResult();
            process.setProcessCode(v.getProcessCode());
            process.setStatus((short)1);
            processDao.update(process);
        }
        System.out.println(JSON.toJSONString(response));

        return JsonResultUtil.success(response);
    }


    //发起流程实例
    @Override
    public JsonResult createCourseExchangeProcessInstance(CourseInstance courseInstance) throws Exception {
        //根据流程id获取流程的模板信息
        Process process = processDao.getProcessById(courseInstance.getProcessId());

        //获取提交人的信息
        OapiUserGetResponse userGetResponse = getUserByUserid(courseInstance.getUserId(), process.getCorpId());
        List<Long> dep = userGetResponse.getDepartment();

        DefaultDingTalkClient client = new DefaultDingTalkClient(PROCESS_INSTANCE_CREATE);
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(process.getProcessCode());
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 1.班级
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo1.setName("班级");
        vo1.setValue(courseInstance.getClassName());
        formComponentValues.add(vo1);

        //2.日期
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo2.setName("日期");
        vo2.setValue(courseInstance.getOrgTime());
        formComponentValues.add(vo2);

        //3.节次
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo3.setName("节次");
        vo3.setValue(courseInstance.getOrgOrder());
        formComponentValues.add(vo3);

        //4.原有课程名称
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("课程");
        vo4.setValue(courseInstance.getOrgCourseName());
        formComponentValues.add(vo4);

        // 5.日期
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo5.setName("调换日期");
        vo5.setValue(courseInstance.getCTime());
        formComponentValues.add(vo5);

        //6.节次
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo6.setName("调换节次");
        vo6.setValue(courseInstance.getCOrder());
        formComponentValues.add(vo6);

        //7.被调换的课程名称
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo7.setName("调换课程");
        vo7.setValue(courseInstance.getCCourseName());
        formComponentValues.add(vo7);

        //8.申请理由
        // 多行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo8.setName("申请理由");
        vo8.setValue(courseInstance.getReason());
        formComponentValues.add(vo8);

        request.setFormComponentValues(formComponentValues);
        //审批人userid列表
        request.setApprovers(courseInstance.getApprover());
        //审批实例发起人的userid
        request.setOriginatorUserId(courseInstance.getUserId());
        //抄送人userid列表
        request.setCcList(courseInstance.getCopyTo());
        //抄送时间，分为（START, FINISH, START_FINISH）
        request.setCcPosition("FINISH");
        request.setDeptId(dep.get(0));
        request.setAgentId(Long.valueOf(process.getAgentId()));
        //先保存
        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessId(process.getId());
        processInstance.setAgentId(process.getAgentId());
        processInstance.setStatus((short) 1);
        processInstance.setFormComponentValues(JSON.toJSONString(formComponentValues));
        int i = processInstanceDao.insert(processInstance);

        OapiProcessinstanceCreateResponse response = client.execute(request, authenService.getAccessToken(process.getCorpId()));
        if (response.isSuccess()) {
            //更新
            processInstance.setProcessInstanceId(response.getProcessInstanceId());
            processInstanceDao.update(processInstance);
        }

        return JsonResultUtil.success(response);
    }

    @Override
    public JsonResult getCourseProcess(String corpId) throws Exception {
        Process process = processDao.getCourseProcess(corpId);
        return JsonResultUtil.success(process.getId());
    }
}
