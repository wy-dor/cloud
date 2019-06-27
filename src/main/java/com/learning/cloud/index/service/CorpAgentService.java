package com.learning.cloud.index.service;

import com.learning.domain.JsonResult;

public interface CorpAgentService {
    JsonResult getAgentId(String corpId);

    Boolean getIsSchool(String corpId);
}
