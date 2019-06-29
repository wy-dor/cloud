package com.learning.cloud.index.service;

import com.learning.domain.JsonResult;

public interface CorpAgentService {
    String getAgentId(String corpId);

    Boolean getIsSchool(String corpId);
}
