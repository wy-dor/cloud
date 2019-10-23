package com.learning.cloud.ding.picture.controller;

import com.learning.cloud.ding.picture.entity.Picture;
import com.learning.cloud.ding.picture.entity.WangEditor;
import com.learning.cloud.ding.picture.entity.WangEditorUtil;
import com.learning.cloud.ding.picture.service.PictureService;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.log4j.Log4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.rmi.runtime.Log;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    @Value("${spring.system.urlPath)")
    private String urlPath;

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

    @PostMapping("/uploadPics")
    public WangEditor uploadPics(@RequestParam("fileList") java.util.List<MultipartFile> fileList, HttpServletRequest request){
        try {
            List<String> urlList = pictureService.uploadPics(fileList, request);
            String[] strings = new String[urlList.size()];
            urlList.toArray(strings);
            return WangEditorUtil.success(strings);
        } catch (Exception e) {
            return WangEditorUtil.error();
        }
    }

}
