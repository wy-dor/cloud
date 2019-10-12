package com.learning.cloud.index.service;

import com.learning.cloud.index.entity.LoggedRecord;
import com.learning.domain.JsonResult;

/**
 * @Author: yyt
 * @Date: 2019-10-11 15:39
 * @Desc:
 */
public interface LoggedService {
    JsonResult addLoggedRecord(LoggedRecord loggedRecord)throws Exception;

    void AddSchoolScoreFormActivity();
}
