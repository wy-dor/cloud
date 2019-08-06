package com.learning.cloud.news.controller;

import com.learning.cloud.news.entity.News;
import com.learning.cloud.news.service.NewsService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/getAllNews")
    public JsonResult getAllNews(){
        return newsService.getAllNews();
    }

    @GetMapping("/getNewsById")
    public JsonResult getNewsById(long id){
        return newsService.getNewsById(id);
    }

    @PostMapping("/addNews")
    public JsonResult addNews(@RequestParam(value="file",required = false) MultipartFile file, News news) throws Exception {
        return newsService.addNews(news,file);
    }
}
