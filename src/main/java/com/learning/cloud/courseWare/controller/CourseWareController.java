package com.learning.cloud.courseWare.controller;

import com.learning.cloud.courseWare.entity.CourseWare;
import com.learning.cloud.courseWare.service.CourseWareService;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.PayException;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 课件
 */
@RestController
public class CourseWareController {

    @Autowired
    private CourseWareService courseWareService;

    @PostMapping("/addCourseWare")
    public JsonResult addCourseWare(CourseWare courseWare)throws Exception{
        return courseWareService.addCourseWare(courseWare);
    }

    @GetMapping("/getCourseWare")
    public JsonResult getCourseWare(@RequestParam(value="day",required = false) String day,
                                    @RequestParam(value="cdId",required = false) Long cdId
                                    )throws Exception{
        return courseWareService.getCourseWare(day, cdId);
    }

    @GetMapping("/downLoadFile")
    public JsonResult downLoadFile(HttpServletResponse response, String filePath) throws Exception{
        OutputStream out = null;
        InputStream in = null;
        File file = new File(filePath);
        try {
            if(file.exists()){
                String fileName = file.getName();
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment; filename="+fileName);
                in = new FileInputStream(file);
                byte[] by = new byte[in.available()];
                in.read(by);
                out = response.getOutputStream();
                out.write(by);
                file.delete();
            }
        }catch (Exception e){
            throw new PayException(JsonResultEnum.FILE_DOWNLOAD_ERROR);
        }finally {
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        }
        return JsonResultUtil.success();
    }

    @PostMapping("/likeThisCourseWare")
    public JsonResult likeThisCourseWare(@RequestParam(value="id",required = false) Long id)throws Exception{
        return courseWareService.likeThisCourseWare(id);
    }

    @GetMapping("/getMyCourseWare")
    public JsonResult getMyCourseWare(@RequestParam(value="teacherId",required = true) Long teacherId)throws Exception{
        return courseWareService.getMyCourseWare(teacherId);
    }


}
