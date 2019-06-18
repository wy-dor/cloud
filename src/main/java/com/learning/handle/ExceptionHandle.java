package com.learning.handle;

/**
 * Created by yu on 2018/9/16.
 */
//@ControllerAdvice
//@Slf4j
//public class ExceptionHandle {
//
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public JsonResult handle(Exception e){
//        if(e instanceof PayException){
//            PayException payException = (PayException) e;
//            return JsonResultUtil.error(payException.getCode(),payException.getMessage());
//        }else {
//            log.error("[系统异常]{}",e);
//            return JsonResultUtil.error(-1,"未知错误"+e.getMessage());
//        }
//    }
//}
