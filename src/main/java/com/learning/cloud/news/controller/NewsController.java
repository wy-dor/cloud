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

    @GetMapping("/getNews")
    public JsonResult getNews(News news){
        return newsService.getNews(news);
    }

    // 教育局获取所有学校提交的，审批后以及已撤销的新闻
    @GetMapping("/getBureauNews")
    public JsonResult getBureauNews(News news){
        return newsService.getBureauNews(news);
    }

    @GetMapping("/getAllValidNews")
    public JsonResult getAllValidNews(News news){
        return newsService.getAllValidNews(news);
    }

    @GetMapping("/updateTopping")
    public JsonResult updateTopping(Long id){
        return newsService.updateTopping(id);
    }

    @GetMapping("/getNewsById")
    public JsonResult getNewsById(long id){
        return newsService.getNewsById(id);
    }

    @PostMapping("/addNews")
    public JsonResult addNews(@RequestParam(value="file",required = false)MultipartFile file, News news) throws Exception {
        return newsService.addNews(file, news);
    }

    @PostMapping("/updateNews")
    public JsonResult updateNews(@RequestParam(value = "file",required = false)MultipartFile file,News news)  throws Exception{
        return newsService.updateNews(file,news);
    }

    @GetMapping("removeNewsById")
    public JsonResult removeNewsById(Long id){
        return newsService.removeNewsById(id);
    }

    @GetMapping("/deleteNewsById")
    public JsonResult deleteNewsById(Long id){
        return newsService.deleteNewsById(id);
    }

    @PostMapping("/batchUpdateNews")
    public JsonResult batchUpdateNews(String ids,Integer status){
        return newsService.batchUpdateNews(ids,status);
    }

    @GetMapping("/getToppingNews")
    public JsonResult getToppingNews(Integer bureauId){
        return newsService.getToppingNews(bureauId);
    }

    //取消置顶
    @GetMapping("/unsetToppingInNews")
    public JsonResult unsetToppingInNews(Long id){
        return newsService.unsetToppingInNews(id);
    }
}

