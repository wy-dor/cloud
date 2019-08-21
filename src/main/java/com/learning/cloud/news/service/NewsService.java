package com.learning.cloud.news.service;

import com.learning.cloud.news.entity.News;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface NewsService {
    JsonResult addNews(MultipartFile file, News news) throws Exception;

    JsonResult getNews(News news);

    JsonResult getNewsById(long id);

    JsonResult removeNewsById(Long id);

    JsonResult deleteNewsById(Long id);

    JsonResult updateNews(MultipartFile file, News news) throws Exception;

    JsonResult getAllValidNews(News news);

    JsonResult updateTopping(Long newsId);

    JsonResult batchUpdateNews(String ids, Integer status);

    JsonResult getBureauNews(News news);

    JsonResult getToppingNews(Integer bureauId);
}
