package com.learning.cloud.workProcess.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessSaveRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessSaveResponse;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.learning.cloud.index.service.AuthenService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-23 16:19
 * @Desc:
 */
@RestController
public class test {

    @Autowired
    private AuthenService authenService;

    @GetMapping("/createProcess")
    public JsonResult createProcess(String corpId)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/save");

        OapiProcessSaveRequest request = new OapiProcessSaveRequest();
        OapiProcessSaveRequest.SaveProcessRequest saveProcessRequest = new OapiProcessSaveRequest.SaveProcessRequest();
        saveProcessRequest.setDisableFormEdit(true);
        saveProcessRequest.setName("test2.0");
//        saveProcessRequest.setProcessCode("PROC-BE7FC6B2-E95B-45CA-AD9A-A62819EDA2FE");
        saveProcessRequest.setAgentid(292060704L);

        // 注意，每种表单组件，对应的componentName是固定的，参照一下示例代码
        List<OapiProcessSaveRequest.FormComponentVo> formComponentList = Lists.newArrayList();

        // 单行文本框
//        OapiProcessSaveRequest.FormComponentVo singleInput = new OapiProcessSaveRequest.FormComponentVo();
//        singleInput.setComponentName("TextField");
//        OapiProcessSaveRequest.FormComponentPropVo singleInputProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        singleInputProp.setRequired(true);
//        singleInputProp.setLabel("单行输入框");
//        singleInputProp.setPlaceholder("请输入");
//        singleInputProp.setId("TextField-J78F056R");
//        singleInput.setProps(singleInputProp);
//        formComponentList.add(singleInput);

//        // 多行文本框
//        OapiProcessSaveRequest.FormComponentVo multipleInput = new OapiProcessSaveRequest.FormComponentVo();
//        multipleInput.setComponentName("TextareaField");
//        OapiProcessSaveRequest.FormComponentPropVo multipleInputProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        multipleInputProp.setRequired(true);
//        multipleInputProp.setLabel("多行输入框");
//        multipleInputProp.setPlaceholder("请输入");
//        multipleInputProp.setId("TextareaField-J78F056S");
//        multipleInput.setProps(multipleInputProp);
//        formComponentList.add(multipleInput);
//
//        // 金额组件
//        OapiProcessSaveRequest.FormComponentVo moneyComponent = new OapiProcessSaveRequest.FormComponentVo();
//        moneyComponent.setComponentName("MoneyField");
//        OapiProcessSaveRequest.FormComponentPropVo moneyComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        moneyComponentProp.setRequired(true);
//        moneyComponentProp.setLabel("金额（元）大写");
//        moneyComponentProp.setPlaceholder("请输入");
//        moneyComponentProp.setId("MoneyField-J78F0571");
//        moneyComponentProp.setNotUpper("1"); // 是否禁用大写
//        moneyComponent.setProps(moneyComponentProp);
//        formComponentList.add(moneyComponent);
//
//        // 数字输入框
//        OapiProcessSaveRequest.FormComponentVo numberComponent = new OapiProcessSaveRequest.FormComponentVo();
//        numberComponent.setComponentName("NumberField");
//        OapiProcessSaveRequest.FormComponentPropVo numberComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        numberComponentProp.setRequired(true);
//        numberComponentProp.setLabel("数字输入框带单位");
//        numberComponentProp.setPlaceholder("请输入");
//        numberComponentProp.setId("NumberField-J78F057N");
//        numberComponentProp.setUnit("元");
//        numberComponent.setProps(numberComponentProp);
//        formComponentList.add(numberComponent);
//
//        // 计算公式
//        OapiProcessSaveRequest.FormComponentVo calculateComponent = new OapiProcessSaveRequest.FormComponentVo();
//        calculateComponent.setComponentName("CalculateField");
//        OapiProcessSaveRequest.FormComponentPropVo calculateComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        calculateComponentProp.setRequired(true);
//        calculateComponentProp.setLabel("计算公式");
//        calculateComponentProp.setPlaceholder("自动计算数值");
//        calculateComponentProp.setId("CalculateField-JF85Z4ZP");
//        calculateComponent.setProps(calculateComponentProp);
//        formComponentList.add(calculateComponent);
//
//        // 单选框
//        OapiProcessSaveRequest.FormComponentVo selectComponent = new OapiProcessSaveRequest.FormComponentVo();
//        selectComponent.setComponentName("DDSelectField");
//        OapiProcessSaveRequest.FormComponentPropVo selectComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        selectComponentProp.setRequired(true);
//        selectComponentProp.setLabel("单选框");
//        selectComponentProp.setPlaceholder("请输入");
//        selectComponentProp.setId("DDSelectField-J78F056U");
//        selectComponentProp.setOptions(Arrays.asList("a", "b", "c")); // 选项最多200项，每项最多50个字
//        selectComponent.setProps(selectComponentProp);
//        formComponentList.add(selectComponent);
//
//        // 多选框
//        OapiProcessSaveRequest.FormComponentVo multiSelectComponent = new OapiProcessSaveRequest.FormComponentVo();
//        multiSelectComponent.setComponentName("DDMultiSelectField");
//        OapiProcessSaveRequest.FormComponentPropVo multiSelectComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        multiSelectComponentProp.setRequired(true);
//        multiSelectComponentProp.setLabel("多选框");
//        multiSelectComponentProp.setPlaceholder("请输入");
//        multiSelectComponentProp.setId("DDMultiSelectField-J78F056V");
//        multiSelectComponentProp.setOptions(Arrays.asList("a", "b", "c"));
//        multiSelectComponent.setProps(multiSelectComponentProp);
//        formComponentList.add(multiSelectComponent);
//
//        // 日期
//        OapiProcessSaveRequest.FormComponentVo dateComponent = new OapiProcessSaveRequest.FormComponentVo();
//        dateComponent.setComponentName("DDDateField");
//        OapiProcessSaveRequest.FormComponentPropVo dateComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        dateComponentProp.setRequired(true);
//        dateComponentProp.setLabel("日期");
//        dateComponentProp.setPlaceholder("请选择");
//        dateComponentProp.setUnit("小时"); // 小时或天
//        dateComponentProp.setId("DDDateField-J8MTJZVE");
//        dateComponent.setProps(dateComponentProp);
//        formComponentList.add(dateComponent);
//
//        // 日期区间
//        OapiProcessSaveRequest.FormComponentVo dateRangeComponent = new OapiProcessSaveRequest.FormComponentVo();
//        dateRangeComponent.setComponentName("DDDateRangeField");
//        OapiProcessSaveRequest.FormComponentPropVo dateRangeComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        dateRangeComponentProp.setRequired(true);
//        dateRangeComponentProp.setLabel(JSON.toJSONString(Arrays.asList("a", "b")));
//        dateRangeComponentProp.setPlaceholder("请选择");
//        dateRangeComponentProp.setUnit("小时"); // 小时或天
//        dateRangeComponentProp.setId("DDDateRangeField-J78F057Q");
//        dateRangeComponent.setProps(dateRangeComponentProp);
//        formComponentList.add(dateRangeComponent);
//
//        // 关联组件
//        OapiProcessSaveRequest.FormComponentVo relateComponent = new OapiProcessSaveRequest.FormComponentVo();
//        relateComponent.setComponentName("RelateField");
//        OapiProcessSaveRequest.FormComponentPropVo relateComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        relateComponentProp.setRequired(true);
//        relateComponentProp.setLabel("关联审批单");
//        relateComponentProp.setPlaceholder("请选择");
//        relateComponentProp.setId("RelateField-JF85Z4ZO");
//        relateComponentProp.setNotPrint("1");
//        relateComponent.setProps(relateComponentProp);
//        formComponentList.add(relateComponent);
//
//        // 图片
//        OapiProcessSaveRequest.FormComponentVo photoComponent = new OapiProcessSaveRequest.FormComponentVo();
//        photoComponent.setComponentName("DDPhotoField");
//        OapiProcessSaveRequest.FormComponentPropVo photoComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        photoComponentProp.setRequired(true);
//        photoComponentProp.setLabel("图片");
//        photoComponentProp.setId("DDPhotoField-J78F056Y");
//        photoComponent.setProps(photoComponentProp);
//        formComponentList.add(photoComponent);
//
//        // 附件
//        OapiProcessSaveRequest.FormComponentVo attachmentComponent = new OapiProcessSaveRequest.FormComponentVo();
//        attachmentComponent.setComponentName("DDAttachment");
//        OapiProcessSaveRequest.FormComponentPropVo attachmentComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        attachmentComponentProp.setRequired(true);
//        attachmentComponentProp.setLabel("附件");
//        attachmentComponentProp.setId("DDAttachment-J78F0572");
//        attachmentComponent.setProps(attachmentComponentProp);
//        formComponentList.add(attachmentComponent);
//
//        // 内部联系人
//        OapiProcessSaveRequest.FormComponentVo innerContactComponent = new OapiProcessSaveRequest.FormComponentVo();
//        innerContactComponent.setComponentName("InnerContactField");
//        OapiProcessSaveRequest.FormComponentPropVo innerContactComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
//        innerContactComponentProp.setRequired(true);
//        innerContactComponentProp.setLabel("联系人多选");
//        innerContactComponentProp.setChoice(1L); // 是否支持多选 "1" or "0"
//        innerContactComponentProp.setId("InnerContactField-J78F0574");
//        innerContactComponent.setProps(innerContactComponentProp);
//        formComponentList.add(innerContactComponent);
//
//        // 明细组件
//        OapiProcessSaveRequest.FormComponentVo formComponentVo = new OapiProcessSaveRequest.FormComponentVo();
//        // 设置组件名称
//        formComponentVo.setComponentName("TableField");
//
//        // 设置组件属性
//        OapiProcessSaveRequest.FormComponentPropVo prop = new OapiProcessSaveRequest.FormComponentPropVo();
//        prop.setActionName("增加明细");
//        prop.setLabel("明细");
//        prop.setId("TableField-JT435H4C");
//
//        // 明细里需要计算的组件列表
//        List<OapiProcessSaveRequest.FormComponentStatVo> statFieldList = Lists.newArrayList();
//        OapiProcessSaveRequest.FormComponentStatVo statField = new OapiProcessSaveRequest.FormComponentStatVo();
//        statField.setId("NumberField-JT435KJO");
//        statField.setUpper(false);
//        statFieldList.add(statField);
//        prop.setStatField(statFieldList);
//
//        // 明细组件的子组件
//        List<OapiProcessSaveRequest.FormComponentVo2> children = Lists.newArrayList();
//        OapiProcessSaveRequest.FormComponentVo2 form1 = new OapiProcessSaveRequest.FormComponentVo2();
//        form1.setComponentName("TextField");
//        OapiProcessSaveRequest.FormComponentPropVo prop1 = new OapiProcessSaveRequest.FormComponentPropVo();
//        prop1.setPlaceholder("请输入");
//        prop1.setLabel("单行输入框");
//        prop1.setId("TextField-JT435KJN");
//        form1.setProps(prop1);
//
//        OapiProcessSaveRequest.FormComponentVo2 form2 = new OapiProcessSaveRequest.FormComponentVo2();
//        form2.setComponentName("NumberField");
//        OapiProcessSaveRequest.FormComponentPropVo prop2 = new OapiProcessSaveRequest.FormComponentPropVo();
//        prop2.setPlaceholder("请输入数字");
//        prop2.setLabel("数字输入框");
//        prop2.setId("NumberField-JT435KJO");
//        form2.setProps(prop2);

//        children.add(form1);
//        children.add(form2);
//
//        formComponentVo.setChildren(children);
//        formComponentVo.setProps(prop);
//        formComponentList.add(formComponentVo);

        saveProcessRequest.setFormComponentList(formComponentList);
        request.setSaveProcessRequest(saveProcessRequest);

        OapiProcessSaveResponse response = client.execute(request, authenService.getAccessToken(corpId));
        System.out.println(JSON.toJSONString(response));

        return JsonResultUtil.success(response);
    }

    @GetMapping("/sendProcess")
    public JsonResult sendProcess(String processCode, String cropId)throws Exception{
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(processCode);
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 附件
        OapiProcessinstanceCreateRequest.FormComponentValueVo attachmentComponent = new OapiProcessinstanceCreateRequest.FormComponentValueVo();

        /*// qiye
        JSONObject attachmentJson = new JSONObject();
        attachmentJson.put("fileId", "6433971134");
        attachmentJson.put("fileName", "IMG_2644.JPG");
        attachmentJson.put("fileType", "jpg");
        attachmentJson.put("spaceId", "540150983");
        attachmentJson.put("fileSize", "1110333");*/

//        JSONObject attachmentJson = new JSONObject();
//        attachmentJson.put("fileId", "6433971140");
//        attachmentJson.put("fileName", "2644.JPG");
//        attachmentJson.put("fileType", "jpg");
//        attachmentJson.put("spaceId", "1635477658");
//        attachmentJson.put("fileSize", "333");
//
//        JSONArray array = new JSONArray();
//        array.add(attachmentJson);
//        attachmentComponent.setValue(array.toJSONString());
//        attachmentComponent.setName("附件");
//        formComponentValues.add(attachmentComponent);

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo1.setName("单行输入框");
        vo1.setValue("你是猴子搬来的救兵吗？");
        formComponentValues.add(vo1);

//        // 多行输入框
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo2.setName("多行输入框");
//        vo2.setValue("多行输入框value");
//        formComponentValues.add(vo2);
//
//        // 金额
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo3.setName("金额");
//        vo3.setValue("10");
//        formComponentValues.add(vo3);
//
//        // 金额
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo4.setName("数字");
//        vo4.setValue("10");
//        formComponentValues.add(vo4);
//
//        // 计算公式
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo5.setName("计算公式");
//        vo5.setValue("10");
//        formComponentValues.add(vo5);
//
//        // 单选框
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo6.setName("单选框");
//        vo6.setValue("a");
//        formComponentValues.add(vo6);
//
//        // 多选框
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo7.setName("多选框");
//        vo7.setValue("[\"a\"]");
//        formComponentValues.add(vo7);
//
//        // 日期
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo8.setName("日期");
//        vo8.setValue("2019-02-02 19:00");
//        formComponentValues.add(vo8);
//
//        // 日期区间
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo9.setName("日期区间");
//        vo9.setValue("[\"2019-02-02 19:00\",\"2019-02-04 19:00\"]");
//        formComponentValues.add(vo9);
//
//        // 图片
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo10 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo10.setName("图片示例");
//        vo10.setValue("[\"http://xxxxx\"]");
//        formComponentValues.add(vo10);

//        // 内部联系人
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo11 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo11.setName("联系人多选");
//        vo11.setValue("[\"manager3214\"]");
//        formComponentValues.add(vo11);

//        // 明细包含控件
//        // 明细-单行输入框
//        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        ItemName1.setName("单行输入框");
//        ItemName1.setValue("明细-单行输入框value1");
//        // 明细-多行输入框
//        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        ItemName2.setName("数字输入框");
//        ItemName2.setValue("2");
//
//        // 明细-单行输入框
//        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        ItemName3.setName("单行输入框");
//        ItemName3.setValue("明细-单行输入框value2");
//        // 明细-多行输入框
//        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        ItemName4.setName("数字输入框");
//        ItemName4.setValue("3");
//
//        // 明细
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo12 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo12.setName("明细");
//        vo12.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2), Arrays.asList(ItemName3, ItemName4))));
//        formComponentValues.add(vo12);

        request.setFormComponentValues(formComponentValues);
        request.setApprovers("2522580320745516");
        request.setOriginatorUserId("测试");
        request.setCcList("2727281948946090");
        request.setCcPosition("START_FINISH");
        request.setDeptId(-1L);
        request.setAgentId(292060704L);
        OapiProcessinstanceCreateResponse response = client.execute(request,authenService.getAccessToken(cropId));

        System.out.println(JSON.toJSONString(response));
        return JsonResultUtil.success(response);
    }
}
