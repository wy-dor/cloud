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

    @Override
    public JsonResult addNews(News news, MultipartFile file) throws Exception {
        if(file != null){
            Long picId = questionService.reduceImg(file);
            news.setPicId(picId);
        }
        newsDao.insert(news);
        return JsonResultUtil.success();
    }

    @Override
    public JsonResult getAllNews() {
        List<News> newsList = newsDao.getAllNews();
        return JsonResultUtil.success(new PageEntity<>(newsList));
    }

    @Override
    public JsonResult getNewsById(long id) {
        News news = newsDao.getNewsById(id);
        return JsonResultUtil.success(news);
    }
}
