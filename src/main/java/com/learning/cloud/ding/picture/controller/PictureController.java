package com.learning.cloud.ding.picture.controller;

import com.learning.cloud.ding.picture.entity.Picture;
import com.learning.cloud.ding.picture.service.PictureService;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yyt
 * @Date: 2019-03-22 16:09
 * @Desc:
 */
@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getPicById")
    public void getPicById(@RequestParam(value="picId") Long picId, HttpServletResponse response)throws Exception{

        Picture picture = pictureService.getPicById(picId);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(picture.getPic());
        for(int i=0; i<bytes.length;++i){
            if(bytes[i]<0){
                bytes[i]+=256;
            }
        }
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);

        out.flush();
        out.close();
    }

    @PostMapping("/addPic")
    public JsonResult addPic(@RequestParam(value="file") MultipartFile file)throws Exception{
        return pictureService.addPic(file);
    }

    @PostMapping("/reduceAndSaveImg")
    public JsonResult reduceAndSaveImg(@RequestParam(value="file") MultipartFile file)throws Exception{
        Long picId = questionService.reduceImg(file);
        return JsonResultUtil.success(picId);
    }
}
