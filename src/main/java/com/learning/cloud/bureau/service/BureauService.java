package com.learning.cloud.bureau.service;

import com.learning.cloud.bureau.entity.Bureau;
import com.learning.domain.JsonResult;

import java.util.List;

public interface BureauService {
    List<Bureau> getBureaus();

    JsonResult getBureauIdByCorpId(String corpId);
}
