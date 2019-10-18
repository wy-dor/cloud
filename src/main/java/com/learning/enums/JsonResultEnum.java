package com.learning.enums;

import lombok.Getter;

/**
 * Created by yu on 2018/9/16.
 */
public enum JsonResultEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    ERROR(0,"失败"),
    SUCCESS(1,"成功"),
    UNLOGIN(-100,"未登录"),
    LOGIN_SUC(100,"登录成功"),

    NOID(101,"更新失败，缺少主键"),
    UPDATENONE(102,"更新失败或未更新"),
    ERROR_ROLE(103,"权限错误联系管理员"),
    NO_NEED_UPDATE(104,"无需更新"),
    DELETE_ERROR(105,"删除失败"),
    UPDATE_ERROR(106, "更新失败"),

    TIME_CONFLICT(1001,"时间冲突，无法调课"),

    SCORE_TIME_LIMIT(1011,"今日积分类型已达上限"),
    SCORE_SCHOOL_ID_NEED(1012,"请传学校id！"),

    FILE_UPLOAD_ERROR(1021,"文件上传失败！"),
    FILE_DOWNLOAD_ERROR(1022,"文件下载失败！"),

    NO_USER(1035,"没有用户唯一标识，请检查登录方式"),
    OA_LOGIN_ERROR(1031,"OA免登出错"),
    OA_LOGIN_NOT_SYS(1032,"OA免登出错,不是管理员"),
    THIRD_LOGIN_ERROR(1033,"第三方登录出错"),
    NO_FUNCTION_FOR(1034,"没有管理端功能"),
    NO_USER_INFO(1036,"获取用户信息失败"),

    NO_SCORE_ACTION(1041,"没有添加积分项，请联系教育局管理员添加"),

    NO_DEPT_USER_LIST(1051,"部门下用户列表为空"),


    EXIST_INSPECTOR(1061,"登录名重复，不能新增"),
    RESET_ERROR(1062,"修改密码出错"),

    COURSE_NO_PARAMS(1071,"EXCEL模板参数错误"),
    COURSE_NO_FILE(1072,"请传入课程信息"),
    COURSE_NO_SECTION(1073,"设置的生效作息时间错误"),
    ;

    @Getter private Integer code;
    @Getter private String msg;

    JsonResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
