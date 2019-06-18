package com.learning.cloud.config;

public class ApiUrlConstant {
    /**
     * 获取可访问企业相关信息的accessToken的URL
     */
    public static final String URL_GET_CORP_TOKEN = "https://oapi.dingtalk.com/service/get_corp_token";

    /**
     * 获取用户在企业内userId的接口URL
     */
    public static final String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";

    /**
     * 获取永久授权码
     */
    public static final String URL_GET_PERMANENT_CODE = "https://oapi.dingtalk.com/service/get_permanent_code?suite_access_token=";

    /**
     * 激活应用
     */
    public static final String URL_ACTIVE_SUITE = "https://oapi.dingtalk.com/service/activate_suite?suite_access_token=";

    /**
     * 获取第三方应用凭证
     */
    public static final String URL_GET_SUITE_TOKEN = "https://oapi.dingtalk.com/service/get_suite_token";

    /**
     * 获取企业授权信息
     */
    public static final String URL_GET_AUTH_INFO = "https://oapi.dingtalk.com/service/get_auth_info";
}
