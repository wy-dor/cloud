package com.learning.cloud.news.service.impl;

import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.cloud.news.dao.NewsDao;
import com.learning.cloud.news.entity.News;
import com.learning.cloud.news.service.NewsService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
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

    @Autowired
    private SchoolDao schoolDao;

    //状态 0:未提交，1:审核中，2:已驳回，3:已发布，4:已撤销
    @Override
    public JsonResult addNews(MultipartFile file, News news) throws Exception {
        if (file != null) {
            Long picId = questionService.reduceImg(file);
            news.setPicId(picId);
        }
//        Integer schoolId = news.getSchoolId();
//        School bySchoolId = schoolDao.getBySchoolId(schoolId);
//        Integer bureauId = bySchoolId.getBureauId();
//        if (bureauId != null) {
//            news.setBureauId(bureauId);
//        }
        int i = newsDao.insert(news);
        return JsonResultUtil.success("成功增加" + i + "条数据");
    }

    @Override
    public JsonResult getNews(News news) {
        List<News> newsList = newsDao.getNews(news);
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

    //获取所有已发布新闻
    @Override
    public JsonResult getAllValidNews(News news) {
        Integer schoolId = news.getSchoolId();
        Integer bureauId = schoolDao.getBySchoolId(schoolId).getBureauId();
        news.setBureauId(bureauId);
        List<News> allValidNews = newsDao.getAllValidNews(news);
        return JsonResultUtil.success(new PageEntity<>(allValidNews));
    }

    @Override
    public JsonResult updateTopping(Long id) {
        Integer bureauId = newsDao.getNewsById(id).getBureauId();
        if (bureauId != null) {
            News news = new News();
            news.setBureauId(bureauId);
            news.setTopping(1);
            List<News> newsList = newsDao.getNews(news);
            if (newsList != null && newsList.size() > 0) {
                for (News n : newsList) {
                    n.setTopping(0);
                    newsDao.update(n);
                }
            }
            news.setId(id);
            newsDao.update(news);
        }
        return JsonResultUtil.success("置顶成功");
    }

    @Override
    public JsonResult batchUpdateNews(String ids, Integer status) {
        int i = 0;
        String[] split = ids.split(",");
        for (String s : split) {
            long id = Long.parseLong(s);
            News news = new News();
            news.setId(id);
            news.setStatus(status);
            int j = newsDao.update(news);
            if (j == 1) {
                i++;
            }
        }
        return JsonResultUtil.success("成功更新" + i + "条信息");
    }

    @Override
    public JsonResult getBureauNews(News news) {
        List<News> NewsList = newsDao.getBureauNews(news);
        return JsonResultUtil.success(new PageEntity<>(NewsList));
    }

    @Override
    public JsonResult getToppingNews(Integer bureauId) {
        News news = newsDao.getToppingNews(bureauId);
        return JsonResultUtil.success(news);
    }

    @Override
    public JsonResult unsetToppingInNews(Long id) {
        News news = new News();
        news.setId(id);
        news.setTopping(0);
        newsDao.update(news);
        return JsonResultUtil.success("取消置顶成功");
    }
}
