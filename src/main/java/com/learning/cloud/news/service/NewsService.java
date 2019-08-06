package com.learning.cloud.news.service;

import com.learning.cloud.news.entity.News;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface NewsService {
    JsonResult addNews(News news, MultipartFile file) throws Exception;

    JsonResult getAllNews();

    JsonResult getNewsById(long id);
}
