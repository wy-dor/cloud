package com.learning.cloud.ding.picture.service.impl;

import com.learning.cloud.ding.picture.dao.PictureDao;
import com.learning.cloud.ding.picture.entity.Picture;
import com.learning.cloud.ding.picture.service.PictureService;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-03-22 16:43
 * @Desc:
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private QuestionService questionService;

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

    @Override
    public List<String> uploadPics(List<MultipartFile> fileList, HttpServletRequest request) throws Exception {

//            // 获取项目路径
//            String realPath = request.getSession().getServletContext()
//                    .getRealPath("");
        List<String> urlList = new ArrayList<>();
        for (MultipartFile multipartFile : fileList) {
//                InputStream inputStream = multipartFile.getInputStream();
//
//                String contextPath = request.getContextPath();
//
//                // 服务器根目录的路径
//                String path = realPath.replace(contextPath.substring(1),"");
//                // 根目录下新建文件夹upload，存放上传图片
//                String uploadPath = path + "upload";
//                // 获取文件名称
//                String filename = Calendar.getInstance().getTimeInMillis()+"image";
//                // 将文件上传的服务器根目录下的upload文件夹
//                File file = new File(uploadPath);
//                if(!file.exists()){
//                    file.mkdirs();
//                }
//                pictureService.inputStreamToFile(inputStream, file);
            Long picId = questionService.reduceImg(multipartFile);
            // 返回图片访问路径
            String url = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + "/getPicById?picId=" + picId;
            urlList.add(url);
        }

        return urlList;
    }

    //    //这个注入配置文件，主要是因为本地的路径和服务器url路径需要动态配置，可以自己写死，也可以动态获取
//    @Autowired
//    AppConfig appConfig;
//
//    @RequestMapping("/editor")
//    @ResponseBody
//    public Object editor(@RequestParam("file") MultipartFile file) {
//        String fileName = "";
//        if (!file.isEmpty()) {
//            //返回的是字节长度,1M=1024k=1048576字节 也就是if(fileSize<5*1048576)
//            if (file.getSize() > (1048576 * 5)) {
//                return JsonResultUtil.error(1, "文件太大，请上传小于5MB的");
//            }
//            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            if (suffix.equals("")) {
//                return JsonResultUtil.error(2, "上传文件没有后缀，无法识别");
//            }
//
//            fileName = System.currentTimeMillis() + suffix;
//            String saveFileName = appConfig.getFilepath() + "/article/" + fileName;
//            System.out.println(saveFileName);
//            File dest = new File(saveFileName);
//            System.out.println(dest.getParentFile().getPath());
//            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
//                dest.getParentFile().mkdir();
//            }
//            try {
//                file.transferTo(dest); //保存文件
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new WangEditorResponse("1", "上传失败" + e.getMessage());
//                //return ApiReturnUtil.error("上传失败"+e.getMessage());
//            }
//        } else {
//            return new WangEditorResponse("1", "上传出错");
//        }
//        String imgUrl = appConfig.getUrlpath() + "article/" + fileName;
//        return new WangEditorResponse("0", imgUrl);
//    }

    /***
     * 上传图片到服务器 并压缩
     *
     * @param myFile  文件
     * @param request
     * @return
     */
    //第一步，先存源文件
    private Boolean UploadFile(MultipartFile myFile, int width, int height, HttpServletRequest request) {
        Boolean sta = false;
        InputStream is = null;
        FileOutputStream fs = null;
        /** 临时文件夹*/
        String realPath = request.getSession().getServletContext()
                .getRealPath("");
        String imgPath = "upload" + File.separator + "ImgTemp" + File.separator;
        String tempPath = realPath + imgPath;
        System.out.println("old-path-" + tempPath);
        String name = myFile.getOriginalFilename();
        File oldFile = new File(tempPath);
        if (!oldFile.exists()) {
            oldFile.mkdirs();
        }
        /** 处理后文件夹*/
        String newImaPath = "upload" + File.separator + "Img" + File.separator;
        String newPath = realPath + newImaPath;
        System.out.println("new-path-" + newPath);
        File newFile = new File(newPath);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        try {
            /** 先存取源文件*/
            is = myFile.getInputStream();
            fs = new FileOutputStream(tempPath + myFile.getOriginalFilename());
            if (myFile.getSize() > 0) {
                byte[] buffer = new byte[1024 * 1024];
                int bytesum = 0;
                int byteread = 0;
                while ((byteread = is.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                    fs.flush();
                }
                fs.close();
                is.close();
            }
            /** 处理源文件 ，进行压缩再放置到新的文件夹*/
            String oldPath = tempPath + myFile.getOriginalFilename();
            String copyPath = newPath + myFile.getOriginalFilename();
            Boolean ys = zipWidthHeightImageFile(oldPath, copyPath, width,height, 1f);
            if (ys)
                sta = true;
            else
                sta = false;
        } catch (Exception ex) {
            ex.printStackTrace();
            sta = false;
        }
        return sta;
    }

    /***
     * 压缩制定大小图片
     *
     * @param oldPath  临时图片路径
     * @param copyPath 压缩图片保存路径
     * @param width    宽度
     * @param height   高度
     * @param quality  高清度
     * @return
     * @throws Exception
     */
    private Boolean zipWidthHeightImageFile(String oldPath, String copyPath, int width, int height,
                                            float quality) {
        Boolean sta = false;
        File oldFile = new File(oldPath);
        File newFile = new File(copyPath);
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            System.out.println(w);
            int h = srcFile.getHeight(null);
            System.out.println(h);

            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            //String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
            /** 压缩后的文件名 */
            //newImage = filePrex + smallIcon+ oldFile.substring(filePrex.length());

            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(newFile);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
            out.close();
            sta = true;
        } catch (Exception e) {
            e.printStackTrace();
            sta = false;
        }
        return sta;
    }

    //指定图片宽度和高度压缩照片
    public Boolean compressImage(MultipartFile source_file,String target_path) throws IOException {
        try {
            int maxWidth = 400;//限制最大宽
            int maxHeight = 400;//限制最大高
            //获得文件源
            InputStream ins = source_file.getInputStream();
            File file = new File(source_file.getOriginalFilename());
            inputStreamToFile(ins, file);
            Image img = ImageIO.read(file);
            int originWidth = img.getWidth(null);
            int originHeight = img.getHeight(null);
            int targetWidth = 0;//目标宽
            int targetHeight = 0;//目标高
            //宽或者高超过最大上限时进行压缩
            if (originWidth > maxWidth || originHeight > maxHeight) {
                if(originWidth >= originHeight){//横图或方图
                    targetWidth = maxWidth;
                    targetHeight = (int) Math.round(maxWidth * (double)originHeight / (double)originWidth);
                }else{//竖图
                    targetHeight = maxHeight;
                    targetWidth = (int) Math.round(maxHeight * (double)originWidth / (double)originHeight);
                }
            }
            BufferedImage tag = new BufferedImage(targetWidth,targetHeight,BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(img,0,0,targetWidth,targetHeight,null);
            FileOutputStream out = new FileOutputStream(target_path);
            //JPEGImageEncoder可适用于其他图片的类型的转换
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam encoder_param = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            encoder_param.setQuality(1f, true);//质量压缩,范围为0.1-1之间,若压缩尺寸过小,建议压缩质量设为最高1f
            encoder.setJPEGEncodeParam(encoder_param);
            encoder.encode(tag);
            out.close();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
