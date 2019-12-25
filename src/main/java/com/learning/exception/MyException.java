package com.learning.exception;

import com.learning.enums.JsonResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yu on 2018/9/16.
 * spring 只回滚RuntimeException
 */
public class MyException extends RuntimeException {

    @Getter
    @Setter
    private Integer code;

    public MyException(JsonResultEnum jsonResultEnum) {
        super(jsonResultEnum.getMsg());
        this.code = jsonResultEnum.getCode();
    }
}
