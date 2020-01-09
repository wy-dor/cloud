package com.learning.cloud.alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayEcoEduKtBillingModifyModel;
import com.alipay.api.request.AlipayEcoEduKtBillingModifyRequest;
import com.alipay.api.request.AlipayEcoEduKtBillingQueryRequest;
import com.alipay.api.response.AlipayEcoEduKtBillingModifyResponse;
import com.alipay.api.response.AlipayEcoEduKtBillingQueryResponse;
import com.learning.cloud.school.dao.PaySchoolDao;
import com.learning.cloud.school.entity.PaySchool;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.PayException;
import com.learning.cloud.alipay.AlipayClientUtil;
import com.learning.cloud.alipay.entity.QueryParam;
import com.learning.cloud.alipay.service.AliBillService;
import com.learning.cloud.bill.dao.BillDao;
import com.learning.cloud.bill.entity.Bill;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AliBillServiceImpl implements AliBillService {

    @Autowired
    private BillDao billDao;

    @Autowired
    private PaySchoolDao paySchoolDao;

    @Autowired
    private UserDao userDao;

    private static String pid;

    @Value("${ali.isv.pid}")
    public void setPid(String pid) {
        AliBillServiceImpl.pid = pid;
    }

    @Value("${ali.key.appId}")
    private String appId;

    @Override
    public JsonResult getBillState(Integer id) throws Exception {
        Bill bill = billDao.getBillById(id);
        // 请求支付宝获取当前账单状态
        PaySchool paySchool = paySchoolDao.getPaySchoolById(bill.getSchoolId());
        String appAuthToken = "";
        String schoolPid = paySchool.getSchoolPid();
        String outTradeNo = bill.getOrderNo();
        AlipayClient alipayClient = AlipayClientUtil.getClient(appId);
        AlipayEcoEduKtBillingQueryRequest request = new AlipayEcoEduKtBillingQueryRequest();
        QueryParam queryParam = new QueryParam();
        queryParam.setIsv_pid(pid);
        queryParam.setSchool_pid(schoolPid);
        queryParam.setOut_trade_no(outTradeNo);
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent(JSON.toJSONString(queryParam));
        AlipayEcoEduKtBillingQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            log.info("查询账单状态成功");
            String order_status = response.getOrderStatus();
            String out_trade_no = response.getOutTradeNo();
            int i = billDao.updateQueryStatus(order_status,out_trade_no,id);
            if(i>0){
                log.info("查询账单状态更新成功");
                return JsonResultUtil.success(order_status);
            }else {
                log.error("查询账单状态更新失败");
                return JsonResultUtil.error(JsonResultEnum.BILL_QUERY_UPDATE_ERROR);
            }
        }else {
            log.error("查询账单状态失败");
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult modifyBillState(Integer id, Integer status) throws Exception {
        Bill bill = billDao.getBillById(id);
        PaySchool paySchool = paySchoolDao.getPaySchoolById(bill.getSchoolId());
//        String appAuthToken = userDao.getAppAuthTokenById(paySchoolById.getPayUserId());
        String appAuthToken = "";
        String tradeNo = bill.getTradeNo();
        String outTradeNo = bill.getOrderNo();

        // 暂时只提供确认缴费成功功能
        AlipayClient alipayClient = AlipayClientUtil.getClient(appId);
        AlipayEcoEduKtBillingModifyRequest request = new AlipayEcoEduKtBillingModifyRequest();
        AlipayEcoEduKtBillingModifyModel model = new AlipayEcoEduKtBillingModifyModel();
        model.setTradeNo(tradeNo);
        model.setOutTradeNo(outTradeNo);
        model.setStatus(status.toString());
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent(JSON.toJSONString(model));
        AlipayEcoEduKtBillingModifyResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            log.info("确认成功");
            //BILLING_SUCCESS
            billDao.updateBillStateById("BILLING_SUCCESS", id);
            return JsonResultUtil.success();
        }else {
            log.error("确认失败");
            throw new PayException(JsonResultEnum.ERROR);
            //系统处理间隔一段时间后继续确认
            //return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public void modifyBillStateInCallBack(String outTradeNo) throws Exception{
        Bill bill = billDao.getBillByOrderNo(outTradeNo);
        JsonResult jsonResult = modifyBillState(bill.getId(),1);
    }
}
