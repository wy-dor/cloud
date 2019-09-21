package com.learning.cloud.user.parent.service.impl;

import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.parent.service.ParentService;
import com.learning.cloud.util.ServiceResult;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentDao parentDao;


    @Override
    public ServiceResult getByUserId(String userId) {
        Parent byUserId = parentDao.getByUserId(userId);
        return ServiceResult.success(byUserId);
    }

    @Override
    public JsonResult getClassParentNum(Integer classId) {
        Integer parentNum = parentDao.getClassParentNum(classId.toString());
        return JsonResultUtil.success(parentNum);
    }
}
