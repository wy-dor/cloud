package com.learning.cloud.supervision.service.impl;

import com.learning.cloud.ding.picture.dao.PictureDao;
import com.learning.cloud.ding.picture.entity.Picture;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.cloud.supervision.dao.SupervisionDao;
import com.learning.cloud.supervision.entity.Supervision;
import com.learning.cloud.supervision.service.SupervisionService;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SupervisionServiceImpl implements SupervisionService {
    @Autowired
    private SupervisionDao supervisionDao;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PictureDao pictureDao;

    //状态 0:未提交，1:审核中，2:已驳回，3:已发布，4:已撤销
    @Override
    public JsonResult addSupervision(MultipartFile file, Supervision supervision) throws Exception {
        if (file != null) {
            Long picId = questionService.reduceImg(file);
            supervision.setPicId(picId);
        }
        int i = supervisionDao.insert(supervision);
        return JsonResultUtil.success("成功增加" + i + "条数据");
    }

    @Override
    public JsonResult getSupervision(Supervision supervision) {
        List<Supervision> supervisionList = supervisionDao.getSupervision(supervision);
        return JsonResultUtil.success(new PageEntity<>(supervisionList));
    }

    @Override
    public JsonResult getSupervisionById(long id) {
        int i = supervisionDao.increaseClick(id);
        Supervision supervision = supervisionDao.getSupervisionById(id);
        return JsonResultUtil.success(supervision);
    }

    @Override
    public JsonResult removeSupervisionById(Long id) {
        int i = supervisionDao.removeSupervisionById(id);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult deleteSupervisionById(Long id) {
        int i = supervisionDao.deleteSupervisionById(id);
        return JsonResultUtil.success("成功删除" + i + "条数据");
    }

    @Override
    public JsonResult updateSupervision(MultipartFile file, Supervision supervision) throws Exception {
        if (supervision.getPicId() != null && supervision.getPicId() == 0) {
            Long picId = questionService.reduceImg(file);
            supervision.setPicId(picId);
        }
        int i = supervisionDao.update(supervision);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

    //获取所有已发布新闻
    @Override
    public JsonResult getAllValidSupervision(Supervision supervision) {
        List<Supervision> allValidSupervision = supervisionDao.getAllValidSupervision(supervision);
        return JsonResultUtil.success(new PageEntity<>(allValidSupervision));
    }

    @Override
    public JsonResult updateTopping(Long id) {
        Integer bureauId = supervisionDao.getSupervisionById(id).getBureauId();
        if (bureauId != null) {
            Supervision supervision = new Supervision();
            supervision.setBureauId(bureauId);
            supervision.setTopping(1);
            List<Supervision> supervisionList = supervisionDao.getSupervision(supervision);
            if (supervisionList != null && supervisionList.size() > 0) {
                for (Supervision n : supervisionList) {
                    n.setTopping(0);
                    supervisionDao.update(n);
                }
            }
            supervision.setId(id);
            supervisionDao.update(supervision);
        }
        return JsonResultUtil.success("置顶成功");
    }

    @Override
    public JsonResult batchUpdateSupervision(String ids, Integer status) {
        int i = 0;
        String[] split = ids.split(",");
        for (String s : split) {
            long id = Long.parseLong(s);
            Supervision supervision = new Supervision();
            supervision.setId(id);
            supervision.setStatus(status);
            int j = supervisionDao.update(supervision);
            if (j == 1) {
                i++;
            }
        }
        return JsonResultUtil.success("成功更新" + i + "条信息");
    }

    @Override
    public JsonResult getBureauSupervision(Supervision supervision) {
        List<Supervision> SupervisionList = supervisionDao.getBureauSupervision(supervision);
        return JsonResultUtil.success(new PageEntity<>(SupervisionList));
    }

    @Override
    public JsonResult getToppingSupervision(Integer bureauId) {
        Supervision supervision = supervisionDao.getToppingSupervision(bureauId);
        return JsonResultUtil.success(supervision);
    }

    @Override
    public JsonResult setSupervisor(User user) {
        int i = userDao.updateSupervisor(user);
        return JsonResultUtil.success("成功设置" + i +"个督学");
    }

    @Override
    public JsonResult getSupervisorIdentity(User user) {
        User bySchoolRoleIdentity = userDao.getBySchoolRoleIdentity(user);
        return JsonResultUtil.success(bySchoolRoleIdentity);
    }

    @Override
    public JsonResult unsetToppingInSupervision(Long id) {
        Supervision supervision = new Supervision();
        supervision.setId(id);
        supervision.setTopping(0);
        supervisionDao.update(supervision);
        return JsonResultUtil.success("取消置顶成功");
    }

    @Override
    public JsonResult getPicsForSupervision(Supervision supervision) {
        List<Picture> pictureList = new ArrayList<>();
        List<Long> picIdList = supervisionDao.getPicsForSupervision(supervision);
        for (Long picId : picIdList) {
            Picture pic = pictureDao.getPic(picId);
            pictureList.add(pic);
        }
        return JsonResultUtil.success(pictureList);
    }


}
