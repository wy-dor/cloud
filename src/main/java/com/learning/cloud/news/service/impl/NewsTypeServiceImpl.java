package com.learning.cloud.news.service.impl;

import com.learning.cloud.news.dao.NewsTypeDao;
import com.learning.cloud.news.entity.NewsType;
import com.learning.cloud.news.service.NewsTypeService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NewsTypeServiceImpl implements NewsTypeService {

    @Autowired
    private NewsTypeDao newsTypeDao;

    @Override
    public JsonResult addNewsType(NewsType newsType) {
        int i = newsTypeDao.insert(newsType);
        return JsonResultUtil.success("成功增加" + i + "条数据");
    }

    @Override
    public JsonResult getNewsTypeByTypeId(Integer typeId) {
        NewsType newsType = newsTypeDao.getByTypeId(typeId);
        return JsonResultUtil.success(newsType);
    }

    @Override
    public JsonResult getNewsTypesByParentId(NewsType newsType) {
        List<NewsType> newsTypeList = newsTypeDao.getByParentId(newsType);
        return JsonResultUtil.success(new PageEntity<>(newsTypeList));
    }

    @Override
    public JsonResult deleteNewsType(Integer typeId) {
        NewsType byTypeId = newsTypeDao.getByTypeId(typeId);
        int i = 0;
        if(byTypeId.getParentId() == 0){
            i = newsTypeDao.deleteByParentId(typeId);
        }else{
            i = newsTypeDao.deleteByTypeId(typeId);
        }
        return JsonResultUtil.success("成功删除" + i + "条数据");
    }

    @Override
    public JsonResult updateNewsType(NewsType newsType) {
        int i = newsTypeDao.update(newsType);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }


}
