package com.learning.cloud.user.admin.service;

import com.learning.cloud.util.ServiceResult;
import com.taobao.api.ApiException;

public interface AdministratorService {

    ServiceResult getRoleSimpleList(long roleId, String accessToken) throws ApiException;
}
