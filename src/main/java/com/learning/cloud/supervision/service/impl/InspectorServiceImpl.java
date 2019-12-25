package com.learning.cloud.supervision.service.impl;

import com.learning.cloud.supervision.dao.InspectorDao;
import com.learning.cloud.supervision.entity.Inspector;
import com.learning.cloud.supervision.service.InspectorService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.MyException;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-11 23:41
 * @Desc:
 */
@Service
public class InspectorServiceImpl implements InspectorService {

    @Autowired
    private InspectorDao inspectorDao;

    @Override
    public JsonResult addInspector(Inspector inspector) throws Exception {
        // 判断不能重名
        int count = inspectorDao.getInspectorByLogin(inspector);
        if (count > 0) {
            throw new MyException(JsonResultEnum.EXIST_INSPECTOR);
        }
        inspector.setLogin(inspector.getLogin().replaceAll(" ", ""));
        int i = inspectorDao.addInspector(inspector);
        if (i > 0) {
            return JsonResultUtil.success(inspector.getId());
        } else {
            return JsonResultUtil.error(JsonResultEnum.ERROR);
        }
    }

    @Override
    public JsonResult deleteInspector(Long id) throws Exception {
        int i = inspectorDao.deleteInspector(id);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.DELETE_ERROR);
        }
    }

    @Override
    public JsonResult updateInspector(Inspector inspector) throws Exception {
        int i = inspectorDao.updateInspector(inspector);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public JsonResult getInspector(Inspector inspector) throws Exception {
        List<Inspector> inspectors = inspectorDao.getInspector(inspector);
        return JsonResultUtil.success(new PageEntity<>(inspectors));
    }

    @Override
    public JsonResult loginInspector(String login, String password) throws Exception {
        Inspector inspector = inspectorDao.loginInspector(login, password);
        return JsonResultUtil.success(inspector);
    }

    @Override
    public JsonResult resetPassword(Inspector inspector) throws Exception {
        int i = inspectorDao.resetPassword(inspector);
        if (i > 0) {
            return JsonResultUtil.success();
        } else {
            return JsonResultUtil.error(JsonResultEnum.RESET_ERROR);
        }
    }

}
