package com.learning.cloud.courseWare.service.impl;

import com.learning.cloud.courseWare.dao.CourseWareDao;
import com.learning.cloud.courseWare.entity.CourseWare;
import com.learning.cloud.courseWare.service.CourseWareService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class CourseWareServiceImpl implements CourseWareService {

    @Autowired
    private CourseWareDao courseWareDao;

    //新增课件
    @Override
    public JsonResult addCourseWare(CourseWare courseWare) throws Exception {
        //获取文件，将文件保存在服务器路径下
//        if(file!=null&&!file.isEmpty()){
//            String filePath = SaveFile(file, courseWare.getTeacherId());
//            courseWare.setFilePath(filePath);
//            int i = courseWareDao.addCourseWare(courseWare);
//            return JsonResultUtil.success(courseWare.getId());
//        }
        //使用钉盘，不在服务器保存文件
        int i = courseWareDao.addCourseWare(courseWare);
        return JsonResultUtil.success(courseWare.getId());
    }

    private String SaveFile(MultipartFile file, Long teacherId) {
        try{
            if(file!=null&&!file.isEmpty()){
                String fileName = file.getOriginalFilename();
                String rootPath = System.getProperty("user.dir")+"/ware_"+String.valueOf(teacherId);
                String filePath = (rootPath+"/"+fileName).replace("\\","/");
                File fp = new File(new File(filePath).getParent());
                if(!fp.exists()){
                    fp.mkdirs();
                }
                FileOutputStream out = new FileOutputStream(filePath);
                InputStream is = null;
                try {
                    is = file.getInputStream();
                    byte[] b=new byte[is.available()];
                    is.read(b);
                    out.write(b);
                    return filePath ;
                }catch (Exception e){

                }finally {
                    if (is != null) {
                        is.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    out.close();

                }

            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
       return null;
    }

    //获取课件
    @Override
    public JsonResult getCourseWare(String day, Long cdId) throws Exception {
        List<CourseWare> courseWares = courseWareDao.getCourseWare(day, cdId);
        return JsonResultUtil.success(courseWares);
    }

    @Override
    public JsonResult likeThisCourseWare(Long id) throws Exception {
        int i = courseWareDao.likeThisCourseWare(id);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(JsonResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public JsonResult getMyCourseWare(Long teacherId) throws Exception {
        List<CourseWare> courseWares = courseWareDao.getMyCourseWare(teacherId);
        return JsonResultUtil.success(courseWares);
    }
}
