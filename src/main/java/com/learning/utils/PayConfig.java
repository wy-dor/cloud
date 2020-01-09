package com.learning.utils;

/**
 * @Author: yyt
 * @Date: 2019-01-10 11:25
 * @Desc: 支付数据字典，常量定义
 */
public class PayConfig {
    //支付方式
    public static final short FACE_ALI_PAY = 1;//支付宝当面付
    public static final short FACE_WE_CHAT_PAY = 2;//微信当面付
    public static final short ALI_PAY = 3;//支付宝账单

    //支付宝账单状态
    public static final String NOT_PAY = "待缴费";
    public static final String PAYING = "支付中";
    public static final String PAY_SUCCESS = "支付成功，处理中";
    public static final String BILLING_SUCCESS = "缴费成功 ";
    public static final String TIMEOUT_CLOSED = "逾期关闭账单";
    public static final String ISV_CLOSED = "账单关闭";
    public static final String FAILURE = "发送失败";

    //微信支付状态
    public static final String SUCCESS = "支付成功";
    public static final String REFUND = "转入退款";
    public static final String NOTPAY = "未支付";
    public static final String CLOSED = "已关闭";
    public static final String REVOKED = "已撤销";//（付款码支付）
    public static final String USERPAYING = "用户支付中";//（付款码支付）
    public static final String PAYERROR = "支付失败";

    //公众号id
    public static final String APPID="wxf91ab75820b5e0ba";

    public static final String APPSECRET = "157d9b1c1bdce83eabd972877f8d1b17";
    //微信支付分配的商户号
    public static final String MCH_ID="1517515671";


    //通知地址，微信支付成功之后调用后台配置的回调地址
    public static final String NOTIFY_URL="http://www.k12pay.com/notify/wxNotify";

    //服务器ip地址
    public static final String CURRENT_SERVER="47.98.236.3";
    //交易类型
    public static final String TRADE_TYPE="NATIVE";
    //支付密钥
    public static final String SECRET_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASC";
    //微信统一下单URL
    public static final String UNIFIED_WX_PAY_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信查询订单URL
    public static final String QUERY_WX_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";


}
