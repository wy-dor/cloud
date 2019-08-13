package com.learning.cloud.news.service.impl;

import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.cloud.news.dao.NewsDao;
import com.learning.cloud.news.entity.News;
import com.learning.cloud.news.service.NewsService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private QuestionService questionService;

    //状态 0:未提交，1:审核中，2:已驳回，3:已发布，4:已撤销
    @Override
    public JsonResult addNews(MultipartFile file, News news) throws Exception {
        if (file != null) {
            Long picId = questionService.reduceImg(file);
            news.setPicId(picId);
        }
        int i = newsDao.insert(news);
        return JsonResultUtil.success("成功增加" + i + "条数据");
    }

    @Override
    public JsonResult getAllNews(News news) {
        List<News> newsList = newsDao.getAllNews(news);
        return JsonResultUtil.success(new PageEntity<>(newsList));
    }

    @Override
    public JsonResult getNewsById(long id) {
        News news = newsDao.getNewsById(id);
        return JsonResultUtil.success(news);
    }

    @Override
    public JsonResult removeNewsById(Long id) {
        int i = newsDao.removeNewsById(id);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult deleteNewsById(Long id) {
        int i = newsDao.deleteNewsById(id);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult updateNews(MultipartFile file, News news) throws Exception {
        if (news.getPicId() != null && news.getPicId() == 0) {
            Long picId = questionService.reduceImg(file);
            news.setPicId(picId);
        }
        int i = newsDao.update(news);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }
}
