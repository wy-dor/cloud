package com.learning.cloud.workProcess.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessSaveRequest;
import com.dingtalk.api.response.OapiProcessSaveResponse;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.AuthenService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.workProcess.dao.ProcessDao;
import com.learning.cloud.workProcess.entity.Process;
import com.learning.cloud.workProcess.service.ProcessService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: yyt
 * @Date: 2019-10-09 22:42
 * @Desc:
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private BureauDao bureauDao;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private CorpAgentDao corpAgentDao;

    @Autowired
    private ProcessDao processDao;

    private static String PROCESS_SAVE = "https://oapi.dingtalk.com/topapi/process/save";

    @Override
    public JsonResult createProcessExample(String corpId) throws Exception {
        // 根据bureauId获取教育局信息
        Bureau bureau = bureauDao.getByCorpId(corpId);
        CorpAgent corpAgent = corpAgentDao.getByCorpId(corpId);

        DingTalkClient client = new DefaultDingTalkClient(PROCESS_SAVE);

        OapiProcessSaveRequest request = new OapiProcessSaveRequest();
        OapiProcessSaveRequest.SaveProcessRequest saveProcessRequest = new OapiProcessSaveRequest.SaveProcessRequest();
        saveProcessRequest.setDisableFormEdit(true);
        saveProcessRequest.setName("内部公文流转");
//        saveProcessRequest.setProcessCode("PROC-89DFDDB4-9F2C-4E5F-B5FE-1496B8742CB8");
        saveProcessRequest.setAgentid(Long.valueOf(corpAgent.getAgentId()));

        // 注意，每种表单组件，对应的componentName是固定的，参照一下示例代码
        List<OapiProcessSaveRequest.FormComponentVo> formComponentList = Lists.newArrayList();

        //1.收文日期
        OapiProcessSaveRequest.FormComponentVo dateComponent = new OapiProcessSaveRequest.FormComponentVo();
        dateComponent.setComponentName("DDDateField");
        OapiProcessSaveRequest.FormComponentPropVo dateComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        dateComponentProp.setRequired(false);
        dateComponentProp.setLabel("收文日期");
        dateComponentProp.setPlaceholder("请选择");
        dateComponentProp.setUnit("天"); // 小时或天
        dateComponentProp.setId("DDDateField-J8MTJZVE");
        dateComponent.setProps(dateComponentProp);
        formComponentList.add(dateComponent);

        //2.发文机关
        OapiProcessSaveRequest.FormComponentVo singleInput = new OapiProcessSaveRequest.FormComponentVo();
        singleInput.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo singleInputProp = new OapiProcessSaveRequest.FormComponentPropVo();
        singleInputProp.setRequired(true);
        singleInputProp.setLabel("发文机关");
        singleInputProp.setPlaceholder("请输入");
        singleInputProp.setId("TextField-J78F056R");
        singleInput.setProps(singleInputProp);
        formComponentList.add(singleInput);

        //3.公文标题
        OapiProcessSaveRequest.FormComponentVo multipleInput = new OapiProcessSaveRequest.FormComponentVo();
        multipleInput.setComponentName("TextareaField");
        OapiProcessSaveRequest.FormComponentPropVo multipleInputProp = new OapiProcessSaveRequest.FormComponentPropVo();
        multipleInputProp.setRequired(true);
        multipleInputProp.setLabel("公文标题");
        multipleInputProp.setPlaceholder("请输入");
        multipleInputProp.setId("TextareaField-J78F056S");
        multipleInput.setProps(multipleInputProp);
        formComponentList.add(multipleInput);

        //4.公文要求

        //5.完成截止日期
        OapiProcessSaveRequest.FormComponentVo dateComponent2 = new OapiProcessSaveRequest.FormComponentVo();
        dateComponent2.setComponentName("DDDateField");
        OapiProcessSaveRequest.FormComponentPropVo dateComponentProp2 = new OapiProcessSaveRequest.FormComponentPropVo();
        dateComponentProp2.setRequired(false);
        dateComponentProp2.setLabel("完成截止日期");
        dateComponentProp2.setPlaceholder("请选择");
        dateComponentProp2.setUnit("天"); // 小时或天
        dateComponentProp2.setId("DDDateField-J8MTJZVD");
        dateComponent2.setProps(dateComponentProp2);
        formComponentList.add(dateComponent2);

        //6.附件
        OapiProcessSaveRequest.FormComponentVo attachmentComponent = new OapiProcessSaveRequest.FormComponentVo();
        attachmentComponent.setComponentName("DDAttachment");
        OapiProcessSaveRequest.FormComponentPropVo attachmentComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        attachmentComponentProp.setRequired(true);
        attachmentComponentProp.setLabel("附件");
        attachmentComponentProp.setId("DDAttachment-J78F0572");
        attachmentComponent.setProps(attachmentComponentProp);
        formComponentList.add(attachmentComponent);

        saveProcessRequest.setFormComponentList(formComponentList);
        request.setSaveProcessRequest(saveProcessRequest);

        OapiProcessSaveResponse response = client.execute(request, authenService.getAccessToken(corpId));
        //保存流程创建模板
        if(response.isSuccess()){
            Process process = new Process();
            Map responseParams = response.getParams();
            String params = JSON.toJSONString(responseParams.get("saveProcessRequest"));
            OapiProcessSaveResponse.ProcessTopVo v = response.getResult();
            process.setAgentId(corpAgent.getAgentId());
            process.setBureauId(bureau.getId());
            process.setCorpId(corpId);
            process.setFormComponentList(params);
            process.setProcessCode(v.getProcessCode());
            process.setStatus((short)1);
            process.setDisableFormEdit("true");
            process.setName("内部公文流转");
            process.setDescription("内部公文流转模板");
            process.setFormComponentList(params);
            processDao.save(process);
        }
        System.out.println(JSON.toJSONString(response));

        return JsonResultUtil.success(response);
    }

    @Override
    public JsonResult getSchoolByBureau(Integer id) throws Exception {
        List<School> schools = schoolDao.getSchoolByBureau(id);
        return JsonResultUtil.success(schools);
    }

    @Override
    public JsonResult getProcessById(Integer id) throws Exception {
        Process process = processDao.getProcessById(id);
        return JsonResultUtil.success(process);
    }

    @Override
    public JsonResult getProcessByBureauId(String bureauId) throws Exception {
        Process process = processDao.getProcessByBureauId(bureauId);
        return JsonResultUtil.success(process);
    }
}
