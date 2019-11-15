package com.learning.cloud.workProcess.service;

import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-10-09 22:41
 * @Desc:
 */
public interface ProcessService {
    JsonResult createProcessExample(String corpId)throws Exception;

    JsonResult getSchoolByBureau(Integer id)throws Exception;

    JsonResult getProcessById(Integer id)throws Exception;

    JsonResult getProcessByBureauId(String bureauId)throws Exception;

}
