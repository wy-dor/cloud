package com.learning.enums;

import lombok.Getter;

/**
 * Created by yu on 2018/9/16.
 */
public enum JsonResultEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    ERROR(0, "失败"),
    SUCCESS(1, "成功"),
    UNLOGIN(-100, "未登录"),
    LOGIN_SUC(100, "登录成功"),

    NOID(101, "更新失败，缺少主键"),
    UPDATENONE(102, "更新失败或未更新"),
    ERROR_ROLE(103, "权限错误联系管理员"),
    NO_NEED_UPDATE(104, "无需更新"),
    DELETE_ERROR(105, "删除失败"),
    UPDATE_ERROR(106, "更新失败"),

    TIME_CONFLICT(1001, "时间冲突，无法调课"),

    SCORE_TIME_LIMIT(1011, "今日积分类型已达上限"),
    SCORE_SCHOOL_ID_NEED(1012, "请传学校id！"),

    FILE_UPLOAD_ERROR(1021, "文件上传失败！"),
    FILE_DOWNLOAD_ERROR(1022, "文件下载失败！"),

    NO_USER(1035, "没有用户唯一标识，请检查登录方式"),
    OA_LOGIN_ERROR(1031, "OA免登出错"),
    OA_LOGIN_NOT_SYS(1032, "OA免登出错,不是管理员"),
    THIRD_LOGIN_ERROR(1033, "第三方登录出错"),
    NO_FUNCTION_FOR(1034, "没有管理端功能"),
    NO_USER_INFO(1036, "获取用户信息失败"),

    NO_SCORE_ACTION(1041, "没有添加积分项，请联系教育局管理员添加"),

    NO_DEPT_USER_LIST(1051, "部门下用户列表为空"),


    EXIST_INSPECTOR(1061, "登录名重复，不能新增"),
    RESET_ERROR(1062, "修改密码出错"),

    COURSE_NO_PARAMS(1071, "EXCEL模板参数错误"),
    COURSE_NO_FILE(1072, "请传入课程信息"),
    COURSE_NO_SECTION(1073, "设置的生效作息时间错误"),
    EXCEL_ERROR_DOWNLOAD(1074, "EXCEL下载失败！"),
    COURSE_NO_CLASS(1075, "不存在的班级信息！"),
    TEACHER_NAME_REPEAT(1076, "老师姓名重复无法区分老师！"),
    TEACHER_NO_COURSE(1077, "老师任课信息不能为空！"),

    DING_SPACE_ERROR(1081, "获取钉盘审批空间失败"),
    DING_SYSADMIN_ERROR(1082, "获取企业管理失败"),

    SCHOOL_EXIST(1091,"已经存在的学校名称"),
    SCHOOL_ICON(1092,"学校的校徽或logo只支持png和jpg两种格式，且必须指定一个"),
    CAMPUS_EXIST(1093,"同一个学校不能存在相同的校区"),
    CLASS_EXIST(1094,"同一个学校不能存在相同的班级"),
    STUDENT_EXIST(1095,"同一个班级的同名学生家长手机号不能相同"),
    SCHOOL_NEED(1096,"主校区编号参数缺失"),
    SCHOOL_ID_REQUIRED(1097,"二维码必须根据学校信息获取"),
    ERROR_QR_CODE(1098,"二维码暂时无法生成"),
    BILLS_IN_TIME_ZERO(1099,"当前时间段内没有支付成功的账单"),

    BILL_QUERY_UPDATE_ERROR(1101,"账单状态没有变化"),
    ALI_CALLBACK_ERROR(1102,"支付宝账单回调异常"),
    ALI_MODIFY_ERROR(1103,"支付宝确认异常"),
    BILL_SEND_FAILURE(1104,"账单发送失败"),
    NO_PHONE_INFO(1105,"该学生家长暂未添加手机号"),


    SCHOOL_PID(1111,"学校的支付宝商户号没有维护"),
    APP_AUTH_TOKEN(1112,"用户还没有授权，无法提交学校信息"),
    

    ;

    @Getter
    private Integer code;
    @Getter
    private String msg;

    JsonResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
