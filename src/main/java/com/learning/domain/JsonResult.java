package com.learning.domain;

import lombok.Data;

/**
 * Created by yu on 2018/9/16.
 * http请求返回值，统一处理
 */
@Data
public class JsonResult<T> {

    //错误码
    private Integer code;
    //提示信息
    private String msg;
    //返回值
    private T data;

}
