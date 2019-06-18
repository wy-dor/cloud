package com.learning.utils;

import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;

/**
 * Created by yu on 2018/9/16.
 * 返回值统一处理
 */
public class JsonResultUtil {

    public static JsonResult success(Object object){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(1);
        jsonResult.setMsg("成功");
        jsonResult.setData(object);
        return jsonResult;
    }

    public static JsonResult success(){
        return success(null);
    }

    public static JsonResult error(Integer code, String msg){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setMsg(msg);
        return jsonResult;
    }

    public static JsonResult error(JsonResultEnum jsonResultEnum){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(jsonResultEnum.getCode());
        jsonResult.setMsg(jsonResultEnum.getMsg());
        return jsonResult;
    }
}
