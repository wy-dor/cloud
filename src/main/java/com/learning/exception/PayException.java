package com.learning.exception;

import com.learning.enums.JsonResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yu on 2018/9/16.
 * spring 只回滚RuntimeException
 */
public class PayException extends RuntimeException{

    @Getter @Setter private Integer code;

    public PayException(JsonResultEnum jsonResultEnum){
        super(jsonResultEnum.getMsg());
        this.code = jsonResultEnum.getCode();
    }
}
