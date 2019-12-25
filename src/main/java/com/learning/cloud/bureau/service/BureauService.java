package com.learning.cloud.bureau.service;

import com.learning.cloud.bureau.entity.Bureau;
import com.learning.domain.JsonResult;

import java.util.List;
import java.util.Map;

public interface BureauService {
    List<Bureau> getBureaus();

    Map<String, Object> getOrgInfoByCorpId(String corpId);
}
