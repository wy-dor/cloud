package com.learning.cloud.news.dao;

import com.learning.cloud.news.entity.NewsType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NewsTypeDao {

    int insert(NewsType record);

    NewsType getByTypeId(Integer typeId);

    List<NewsType> getByParentId(NewsType newsType);

    int deleteByTypeId(Integer typeId);

    int deleteByParentId(Integer typeId);

    int update(NewsType newsType);
}
