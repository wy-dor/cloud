package com.learning.cloud.ding.picture.service.impl;

import com.learning.cloud.ding.picture.dao.PictureDao;
import com.learning.cloud.ding.picture.entity.Picture;
import com.learning.cloud.ding.picture.service.PictureService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Author: yyt
 * @Date: 2019-03-22 16:43
 * @Desc:
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public Picture getPicById(Long picId) throws Exception {
        return pictureDao.getPic(picId);
    }

    @Override
    public JsonResult addPic(MultipartFile file) throws Exception {
        Picture picture = new Picture();
        //有图片需要先上传图片
        if(!file.isEmpty()){
            BASE64Encoder encoder = new BASE64Encoder();
            //base64转图
            String pic = encoder.encode(file.getBytes());
            picture.setPic(pic);
            int i = pictureDao.addPic(picture);
        }
        return JsonResultUtil.success(picture.getId());
    }


}
