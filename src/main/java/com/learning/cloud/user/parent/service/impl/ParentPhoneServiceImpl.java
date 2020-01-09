package com.learning.cloud.user.parent.service.impl;


import com.learning.cloud.user.parent.dao.ParentPhoneDao;
import com.learning.cloud.user.parent.entity.ParentPhone;
import com.learning.cloud.user.parent.service.ParentPhoneService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParentPhoneServiceImpl implements ParentPhoneService {

    @Autowired
    private ParentPhoneDao parentPhoneDao;

    @Override
    public JsonResult getParentPhoneByUserId(String userId) {
        ParentPhone pp = parentPhoneDao.getByUserId(userId);
        return JsonResultUtil.success(pp);
    }

    @Override
    public JsonResult getParentPhoneById(Long id) {
        ParentPhone pp = parentPhoneDao.getById(id);
        return JsonResultUtil.success(pp);
    }

    @Override
    public JsonResult addParentPhone(ParentPhone parentPhone) {
        int insert = parentPhoneDao.insert(parentPhone);
        if(insert == 1){
            return JsonResultUtil.success(parentPhone.getId());
        }else{
            return JsonResultUtil.error(0,"添加失败");
        }
    }

    @Override
    public JsonResult updateParentPhone(ParentPhone parentPhone) {
        int update = parentPhoneDao.update(parentPhone);
        if(update == 1){
            return JsonResultUtil.success("更新成功");
        }else{
            return JsonResultUtil.error(0,"更新失败");
        }
    }

    @Override
    public JsonResult deleteParentPhoneById(Integer id) {
        parentPhoneDao.delete(id);
        return JsonResultUtil.success("删除成功");
    }
}
