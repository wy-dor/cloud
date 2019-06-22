package com.learning.cloud.user.teacher.service;

import com.learning.cloud.util.ServiceResult;

public interface TeacherService {

    ServiceResult getByUserId(String userId);

}
