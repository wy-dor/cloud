package com.learning.cloud.bizData.service;

import com.learning.cloud.bizData.entity.SyncBizDataMedium;
import com.learning.domain.JsonResult;

public interface BizDataMediumService {
    JsonResult initBizDataMedium(SyncBizDataMedium syncBizDataMedium) throws Exception;
}
