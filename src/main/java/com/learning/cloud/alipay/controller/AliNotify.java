//package com.learning.cloud.alipay.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.learning.cloud.alipay.service.AliBillService;
//import com.learning.cloud.bill.service.BillService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * @Author: yyt
// * @Date: 2019-01-02 16:09
// * @Desc: 支付宝账单回调
// */
//@RestController
//@Slf4j
//@Transactional
//public class AliNotify {
//
//    @Autowired
//    private BillService billService;
//
//    @Autowired
//    private AliBillService aliBillService;
//
//    private static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
//
//    //教育缴费账单回调
//    @RequestMapping(value = "/notify/payNotify")
//    public String getPayNotify(HttpServletRequest request){
//        try {
//            //转码
//            request.setCharacterEncoding("GBK");
//            log.info("触发回调");
//            Map<String, String> params = new HashMap<>();
//            Map requestParams = request.getParameterMap();
//            //处理回调值
//            for(Iterator iter = requestParams.keySet().iterator(); iter.hasNext();){
//                String name = (String) iter.next();
//                String[] values = (String[]) requestParams.get(name);
//                String valueStr = "";
//                for(int i = 0; i<values.length; i++){
//                    valueStr = (i == values.length-1) ? valueStr + values[i]: valueStr + values[i] + ",";
//                }
//                params.put(name, valueStr);
//            }
//            log.info(JSON.toJSONString(params));
//
//            //获取支付宝的通知返回参数
//            //系统交易号 orderNo(发送账单后返回)
//            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//            //支付宝交易号
//            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//            //交易状态
//            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
//            String gmtPayment = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"),"UTF-8");
//            String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
//
//            //系统验签
//            boolean flag = AlipaySignature.rsaCheckV1(params, alipay_public_key,"GBK","RSA");
//            if(flag) {
//                //更新账单状态，回填支付宝订单号，数据库字段
//                if (tradeStatus.equals("TRADE_SUCCESS")) {
//                    //更新账单
//                    log.info("更新账单状态");
//                    billService.updateCallBackByOrderNo(tradeNo, gmtPayment, totalAmount, outTradeNo);
//                    //确认账单状态
//                    log.info("确认账单同步接口");
//                    aliBillService.modifyBillStateInCallBack(outTradeNo);
//                }
//                return "success";
//            }else{
//                return "response fail";
//            }
//        }catch (Exception e){
//            log.error("支付宝账单回调异常:"+e.getMessage());
//            return "response fail";
//        }
//
//    }
//}
