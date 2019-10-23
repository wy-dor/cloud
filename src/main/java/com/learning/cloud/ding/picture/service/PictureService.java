package com.learning.cloud.ding.picture.service;

import com.learning.cloud.ding.picture.entity.Picture;
import com.learning.cloud.ding.picture.entity.WangEditor;
import com.learning.domain.JsonResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-03-22 16:42
 * @Desc:
 */
public interface PictureService {
    Picture getPicById(Long picId)throws Exception;

    JsonResult addPic(MultipartFile file)throws Exception;

    List<String> uploadPics(List<MultipartFile> fileList, HttpServletRequest request) throws Exception;
}
