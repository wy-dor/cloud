package com.learning.cloud.workProcess.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.admin.service.AdminService;
import com.learning.cloud.workProcess.dao.ProcessDao;
import com.learning.cloud.workProcess.dao.ProcessInstanceDao;
import com.learning.cloud.workProcess.entity.Attachment;
import com.learning.cloud.workProcess.entity.Process;
import com.learning.cloud.workProcess.entity.ProcessInstance;
import com.learning.cloud.workProcess.entity.ProcessValue;
import com.learning.cloud.workProcess.service.ProcessInstanceService;
import com.learning.cloud.workProcess.service.ProcessService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.learning.cloud.dingCommon.DingUtils.getUserByUserid;

/**
 * @Author: yyt
 * @Date: 2019/10/21 3:49 下午
 * @Desc: 创建流程实例
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Autowired
    private ProcessInstanceDao processInstanceDao;

    @Autowired
    private ProcessDao processDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private ProcessService processService;

    @Autowired
    private AdminService adminService;

    private static String PROCESS_INSTANCE_CREATE = "https://oapi.dingtalk.com/topapi/processinstance/create";

    private static String PROCESS_INSTANCE_GET = "https://oapi.dingtalk.com/topapi/processinstance/get";

    @Override
    public JsonResult createProcessInstance(ProcessValue processValue) throws Exception {
        //根据流程id获取流程的模板信息
        Process process = processDao.getProcessById(processValue.getProcessId());

        //获取提交人的信息
        OapiUserGetResponse userGetResponse = getUserByUserid(processValue.getUserId(), process.getCorpId());
        List<Long> dep = userGetResponse.getDepartment();

        DefaultDingTalkClient client = new DefaultDingTalkClient(PROCESS_INSTANCE_CREATE);
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(process.getProcessCode());
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        //1.收文日期
        // 日期
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo1.setName("收文日期");
        vo1.setValue(processValue.getCreateTime());
        formComponentValues.add(vo1);

        //2.发文机关
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo2.setName("发文机关");
        vo2.setValue(processValue.getInstitution());
        formComponentValues.add(vo2);

        //3.公文标题
        // 多行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo3.setName("公文标题");
        vo3.setValue(processValue.getTitle());
        formComponentValues.add(vo3);

        //5.完成截止日期
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("完成截止日期");
        vo4.setValue(processValue.getDeadLine());
        formComponentValues.add(vo4);

        //6.附件

        OapiProcessinstanceCreateRequest.FormComponentValueVo attachmentComponent = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        JSONObject attachmentJson = new JSONObject();
        Attachment attachment = JSONObject.parseObject(processValue.getAttachment(), Attachment.class);
        if (attachment != null) {
            attachmentJson.put("fileId", attachment.getFileId());
            attachmentJson.put("fileName", attachment.getFileName());
            attachmentJson.put("fileType", attachment.getFileType());
            attachmentJson.put("spaceId", attachment.getSpaceId());
            attachmentJson.put("fileSize", attachment.getFileSize());
        } else {
            attachmentJson.put("fileId", "");
            attachmentJson.put("fileName", "");
            attachmentJson.put("fileType", "");
            attachmentJson.put("spaceId", "");
            attachmentJson.put("fileSize", "");
        }


        JSONArray array = new JSONArray();
        array.add(attachmentJson);
        attachmentComponent.setValue(array.toJSONString());
        attachmentComponent.setName("附件");
        formComponentValues.add(attachmentComponent);


        request.setFormComponentValues(formComponentValues);
        //审批人userid列表
        request.setApprovers(processValue.getApprover());
        //审批实例发起人的userid
        request.setOriginatorUserId(processValue.getUserId());
        //抄送人userid列表
        request.setCcList(processValue.getCopyTo());
        //抄送时间，分为（START, FINISH, START_FINISH）
        request.setCcPosition("START_FINISH");
        request.setDeptId(dep.get(0));
        request.setAgentId(Long.valueOf(process.getAgentId()));
        //先保存
        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessId(process.getId());
        processInstance.setAgentId(process.getAgentId());
        processInstance.setStatus((short) 1);
        processInstance.setAttachment(JSON.toJSONString(processValue.getAttachment()));
        processInstance.setFormComponentValues(JSON.toJSONString(formComponentValues));
        int i = processInstanceDao.insert(processInstance);
        //跨组织的公文，
        // 1.创建各个组织的流程
        // 2.生成组织流程实例
        // 3.默认提交人为管理员，审核人为校长。
        // 4.附件需要从服务器转存到各个组织的钉盘
        if (processValue.getSchool() != null) {
            createSchoolProcessInstance(processValue.getSchool(), processValue, processInstance.getId());
        }
        OapiProcessinstanceCreateResponse response = client.execute(request, authenService.getAccessToken(process.getCorpId()));
        if (response.isSuccess()) {
            //跟新
            processInstance.setProcessInstanceId(response.getProcessInstanceId());
            processInstanceDao.update(processInstance);
        }

        return JsonResultUtil.success(response);
    }

    //获取最新的公文编号，暂不实现
    // todo
    @Override
    public JsonResult getLastDocumentNo(Integer bureauId) throws Exception {
        return null;
    }

    //创建学校的固定流程实例
    public void createSchoolProcessInstance(String schoolIds, ProcessValue processValue, Integer parentId) throws Exception {
        //获取各个
        String[] sIds = schoolIds.split(",");
        for (String schoolId : sIds) {
            //获取学校信息
            School school = schoolDao.getBySchoolId(Integer.valueOf(schoolId));
            Process schoolProcess = processDao.getProcessByCorpId(school.getCorpId());
            if (schoolProcess == null) {
                //创建流程
                processService.createProcessExample(school.getCorpId());
            }
            //获取流程信息
            //根据流程id获取流程的模板信息
            Process process = processDao.getProcessByCorpId(school.getCorpId());
            //获取学校管理员
            String userId = adminService.getMainAdmin(school.getCorpId());
            //获取学校管理员的信息
            OapiUserGetResponse userGetResponse = getUserByUserid(userId, school.getCorpId());
            List<Long> dep = userGetResponse.getDepartment();
            //发起流程实例
            DefaultDingTalkClient client = new DefaultDingTalkClient(PROCESS_INSTANCE_CREATE);
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setProcessCode(process.getProcessCode());
            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

            //1.收文日期
            // 日期
            OapiProcessinstanceCreateRequest.FormComponentValueVo vo1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo1.setName("收文日期");
            vo1.setValue(processValue.getCreateTime());
            formComponentValues.add(vo1);

            //2.发文机关
            // 单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo vo2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo2.setName("发文机关");
            vo2.setValue(processValue.getInstitution());
            formComponentValues.add(vo2);

            //3.公文标题
            // 多行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo vo3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo3.setName("公文标题");
            vo3.setValue(processValue.getTitle());
            formComponentValues.add(vo3);

            //5.完成截止日期
            OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo4.setName("完成截止日期");
            vo4.setValue(processValue.getDeadLine());
            formComponentValues.add(vo4);

            //6.附件
            //学校的附件需要单独处理，先上传到钉盘再返回参数
            OapiProcessinstanceCreateRequest.FormComponentValueVo attachmentComponent = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            JSONObject attachmentJson = new JSONObject();
            Attachment attachment = JSONObject.parseObject(processValue.getAttachment(), Attachment.class);
            if (attachment != null) {
                attachmentJson.put("fileId", attachment.getFileId());
                attachmentJson.put("fileName", attachment.getFileName());
                attachmentJson.put("fileType", attachment.getFileType());
                attachmentJson.put("spaceId", attachment.getSpaceId());
                attachmentJson.put("fileSize", attachment.getFileSize());
            } else {
                attachmentJson.put("fileId", "");
                attachmentJson.put("fileName", "");
                attachmentJson.put("fileType", "");
                attachmentJson.put("spaceId", "");
                attachmentJson.put("fileSize", "");
            }

            JSONArray array = new JSONArray();
            array.add(attachmentJson);
            attachmentComponent.setValue(array.toJSONString());
            attachmentComponent.setName("附件");
            formComponentValues.add(attachmentComponent);


            request.setFormComponentValues(formComponentValues);
            //审批人userid列表
            request.setApprovers(userId);
            //审批实例发起人的userid
            request.setOriginatorUserId(userId);
            //抄送人userid列表
//            request.setCcList(processValue.getCopyTo());
            //抄送时间，分为（START, FINISH, START_FINISH）
//            request.setCcPosition("START_FINISH");
            request.setDeptId(dep.get(0));
            request.setAgentId(Long.valueOf(process.getAgentId()));
            //先保存
            ProcessInstance processInstance = new ProcessInstance();
            processInstance.setProcessId(process.getId());
            processInstance.setAgentId(process.getAgentId());
            processInstance.setAttachment(JSON.toJSONString(processValue.getAttachment()));
            processInstance.setFormComponentValues(JSON.toJSONString(formComponentValues));
            int i = processInstanceDao.insert(processInstance);
            OapiProcessinstanceCreateResponse response = client.execute(request, authenService.getAccessToken(process.getCorpId()));
            if (response.isSuccess()) {
                processInstance.setProcessInstanceId(response.getProcessInstanceId());
                processInstance.setStatus((short) 1);
                processInstanceDao.update(processInstance);

            }

        }
    }

    //获取流程实例详情
    @Override
    public JsonResult getInstanceStatus(String processInstanceId, String corpId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient(PROCESS_INSTANCE_GET);
        OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
        request.setProcessInstanceId(processInstanceId);
        OapiProcessinstanceGetResponse response = client.execute(request, authenService.getAccessToken(corpId));
        if(response.isSuccess()){
            OapiProcessinstanceGetResponse.ProcessInstanceTopVo p = response.getProcessInstance();
            return  JsonResultUtil.success(p);
        }else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }
}
