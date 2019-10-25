package com.learning.cloud.upload;

import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: yyt
 * @Date: 2019/10/24 9:38 上午
 * @Desc: 上传文件到服务器
 */
@RestController
public class SysUpload {

    private static String ATTACHMENT_PATH = "attachment/file";

    private static String ipAddress;

    @Value("${spring.ipAddress}")
    public void setIpAddress(String ipAddress) {
        SysUpload.ipAddress = ipAddress;
    }

    @PostMapping("/uploadFileToSys")
    public JsonResult uploadFileToSys(MultipartFile file) throws Exception {
        String filePath = null;

        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            fileName = "HJ001" + suffix;
            InputStream inputStream = file.getInputStream();
            Path directory = Paths.get(ATTACHMENT_PATH);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.copy(inputStream, directory.resolve(fileName));
            filePath = ipAddress + "/" + ATTACHMENT_PATH + fileName;
        }
        return JsonResultUtil.success(filePath);
    }

}
