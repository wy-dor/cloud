package com.learning.cloud.news.controller;

import com.learning.cloud.news.entity.NewsType;
import com.learning.cloud.news.service.NewsTypeService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsTypeController {
    @Autowired
    private NewsTypeService newsTypeService;

    @PostMapping("/addNewsType")
    public JsonResult addNewsType(NewsType newsType) {
        return newsTypeService.addNewsType(newsType);
    }

    @PostMapping("updateNewsType")
    public JsonResult updateNewsType(NewsType newsType) {
        return newsTypeService.updateNewsType(newsType);
    }

    @GetMapping("/getNewsTypeByTypeId")
    public JsonResult getNewsTypeByTypeId(Integer typeId) {
        return newsTypeService.getNewsTypeByTypeId(typeId);
    }

    //根级类型的parentId为0
    @GetMapping("/getNewsTypesByParentId")
    public JsonResult getNewsTypesByParentId(NewsType newsType) {
        return newsTypeService.getNewsTypesByParentId(newsType);
    }

    //删除指定类型及其下子类型
    @GetMapping("/deleteNewsType")
    public JsonResult deleteNewsType(Integer typeId) {
        return newsTypeService.deleteNewsType(typeId);
    }

    @GetMapping("/getAllNewsType")
    public JsonResult getAllNewsType(NewsType newsType) {
        return newsTypeService.getAllNewsType(newsType);
    }
}
