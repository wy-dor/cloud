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




    ;

    @Getter private Integer code;
    @Getter private String msg;

    JsonResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
