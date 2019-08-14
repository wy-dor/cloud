package com.learning.cloud.news.service;

import com.learning.cloud.news.entity.NewsType;
import com.learning.domain.JsonResult;

public interface NewsTypeService {
    JsonResult addNewsType(NewsType newsType);

    JsonResult getNewsTypeByTypeId(Integer typeId);

    JsonResult getNewsTypesByParentId(NewsType newsType);

    JsonResult deleteNewsType(Integer typeId);

    JsonResult updateNewsType(NewsType newsType);

    JsonResult getAllNewsType(NewsType newsType);
}
