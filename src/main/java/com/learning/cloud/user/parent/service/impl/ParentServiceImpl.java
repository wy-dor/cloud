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

import java.util.List;

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

    @Override
    public int removeParentsInClass(Integer classId) {
        List<Parent> parentList = parentDao.listParentInClass(classId.toString());
        int i = 0;
        for (Parent parent : parentList) {
            int j = removeParentInClass(classId, parent);
            i += j;
        }
        return i;
    }

    @Override
    public int removeParentInClass(Integer classId, Parent parent) {
        String classId1 = parent.getClassId();
        String classesStr = "," + classId1 + ",";
        String replace = classesStr.replace(classId + ",", "");
        String substring = "";
        if (!replace.equals(",")) {
            substring = replace.substring(1, replace.lastIndexOf(","));
        }
        int i = 0;
        if (substring.equals("")) {
            i = parentDao.delete(parent.getId());
        } else {
            parent.setClassId(substring);
            i = parentDao.update(parent);
        }
        return i;
    }
}
