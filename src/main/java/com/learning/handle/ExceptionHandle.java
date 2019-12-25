package com.learning.handle;

import com.learning.domain.JsonResult;
import com.learning.exception.MyException;
import com.learning.utils.JsonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yu on 2018/9/16.
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult handle(Exception e) {
        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            return JsonResultUtil.error(myException.getCode(), myException.getMessage());
        } else {
            log.error("[系统异常]{}", e);
            return JsonResultUtil.error(-1, "未知错误" + e.getMessage());
        }
    }
}
