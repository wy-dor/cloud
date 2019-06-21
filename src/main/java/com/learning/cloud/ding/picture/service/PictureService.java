package com.learning.cloud.ding.picture.service;

import com.learning.cloud.ding.picture.entity.Picture;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yyt
 * @Date: 2019-03-22 16:42
 * @Desc:
 */
public interface PictureService {
    Picture getPicById(Long picId)throws Exception;

    JsonResult addPic(MultipartFile file)throws Exception;
}
