package com.learning.cloud.supervision.service;

import com.learning.cloud.supervision.entity.Inspector;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-09-11 23:40
 * @Desc:
 */
public interface InspectorService {
    JsonResult addInspector(Inspector inspector) throws Exception;

    JsonResult deleteInspector(Long id) throws Exception;

    JsonResult updateInspector(Inspector inspector) throws Exception;

    JsonResult getInspector(Inspector inspector) throws Exception;

    JsonResult loginInspector(String login, String password) throws Exception;

    JsonResult resetPassword(Inspector inspector) throws Exception;
}
