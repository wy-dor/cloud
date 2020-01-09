package com.learning.cloud.user.parent.service;

import com.learning.cloud.user.parent.entity.ParentPhone;
import com.learning.domain.JsonResult;

public interface ParentPhoneService {

    JsonResult getParentPhoneByUserId(String userId);

    JsonResult getParentPhoneById(Long id);

    JsonResult addParentPhone(ParentPhone parentPhone);

    JsonResult updateParentPhone(ParentPhone parentPhone);

    JsonResult deleteParentPhoneById(Integer id);

}
