package com.learning.cloud.user.parent.service;

import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;

public interface ParentService {

    ServiceResult getByUserId(String userId);

    JsonResult getClassParentNum(Integer classId);

    int removeParentsInClass(Integer classId);

    int removeParentInClass(Integer classId, Parent parent);
}
