package com.learning.cloud.news.dao;

import com.learning.cloud.news.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NewsDao {
    int insert(News record);

    List<News> getNews(News news);

    List<News> getAllValidNews(News news);

    News getNewsById(long id);

    int update(News news);

    int removeNewsById(Long id);

    int deleteNewsById(Long id);

    List<News> getBureauNews(News news);

    News getToppingNews(Integer bureauId);
}
