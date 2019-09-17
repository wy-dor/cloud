package com.learning.cloud.ding.question.service.impl;

import com.learning.cloud.ding.picture.dao.PictureDao;
import com.learning.cloud.ding.picture.entity.Picture;
import com.learning.cloud.ding.question.dao.AnswerDao;
import com.learning.cloud.ding.question.dao.QuestionDao;
import com.learning.cloud.ding.question.entity.Answer;
import com.learning.cloud.ding.question.entity.Question;
import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.domain.JsonResult;
import com.learning.domain.PageEntity;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-03-21 17:55
 * @Desc:
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private PictureDao pictureDao;

    /**
     * 新增提问
     * @param question
     * @return
     */
    @Override
    @Transactional
    public JsonResult addQuestion(MultipartFile file, Question question) throws Exception {
        //原始图片储存
        /*if(file!=null){
            Long picId = addPicture(file);
            question.setPicId(picId);
        }*/
        //压缩图片并进行保存
        if(file != null) {
            long picId = reduceImg(file);
            //将压缩后的图片id保存到记录
            question.setSPicId(picId);
        }
        int i = questionDao.addQuestion(question);

        return JsonResultUtil.success(question.getId());
    }

    private Long addPicture(MultipartFile file) throws Exception{
        Picture picture = new Picture();
        //有图片需要先上传图片
        if(!file.isEmpty()){
            BASE64Encoder encoder = new BASE64Encoder();
            //base64转图
            String pic = encoder.encode(file.getBytes());
            picture.setPic(pic);
            int i = pictureDao.addPic(picture);
        }
        return picture.getId();
    }


    /**
     * 根据条件获取提问问题
     * @param question
     * type类型
     * status状态
     * pageSize每页条数
     * pageNum开始页
     */
    @Override
    public JsonResult getQuestion(Question question) throws Exception {
        List<Question> questions = questionDao.getQuestion(question);
        return JsonResultUtil.success(new PageEntity<>(questions));
    }

    /**
     * 回答问题
     *
     * @param file
     * @param answer
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public JsonResult addAnswer(MultipartFile file, Answer answer) throws Exception {
        if(file!=null) {
//            Long picId = addPicture(file);
            Long picId = reduceImg(file);
            answer.setPicId(picId);
        }
        int i = answerDao.addAnswer(answer);
        return JsonResultUtil.success();
    }

    /**
     * 获取问题的回答类容
     * @param answer
     */
    @Override
    public JsonResult getAnswer(Answer answer) throws Exception {
        List<Answer> answers = answerDao.getAnswer(answer.getQuestionId());
        return JsonResultUtil.success(new PageEntity<>(answers));
    }

    /**
     * 删除提问
     * @param id
     */
    @Override
    public JsonResult deleteQuestion(Long id) throws Exception {
        int i = questionDao.deleteQuestion(id);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(1010,"删除失败，请联系管理员");
        }
    }

    /**
     * 获取我参与的提问
     * @param question
     */
    @Override
    public JsonResult getQuestionIn(Question question) throws Exception {
        List<Question> questions = questionDao.getQuestionImIN(question);
        return JsonResultUtil.success(questions);
    }

    /**
     * 压缩图片
     * @return
     */
    @Override
    public Long reduceImg(MultipartFile file)throws Exception{
        int widthDist = 500;
        int heightDist = 500;
        InputStream inputStream = null;
        inputStream = file.getInputStream();
        File toFile = new File(file.getOriginalFilename());
        inputStreamToFile(inputStream, toFile);
        inputStream.close();
        // 开始读取文件并进行压缩
        Image img = ImageIO.read(toFile);

        // 构造一个类型为预定义图像类型之一的 BufferedImage
        BufferedImage tag = new BufferedImage(widthDist,heightDist, BufferedImage.TYPE_INT_RGB);

        //绘制图像
        tag.getGraphics().drawImage(img.getScaledInstance(widthDist, heightDist, Image.SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(tag, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String sp = encoder.encode(outputStream.toByteArray());
        Picture picture = new Picture();
        picture.setPic(sp);
        int i = pictureDao.addPic(picture);
//        questionDao.updateSmallPic(picture.getId(),id);
        return picture.getId();
    }

    @Override
    public JsonResult getQuestionById(Long id) {
        Question question = questionDao.getQuestionById(id);
        return JsonResultUtil.success(question);
    }

    private void inputStreamToFile(InputStream inputStream, File toFile) throws Exception{
        OutputStream outputStream = new FileOutputStream(toFile);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer,0,8192))!=-1){
            outputStream.write(buffer,0,bytesRead);
        }
        outputStream.close();
        inputStream.close();
    }

    /**
     * 关闭提问
     */
    @Override
    public JsonResult closeQuestion(Long id) throws Exception {
        int i = questionDao.closeQuestion(id);
        if(i>0){
            return JsonResultUtil.success();
        }else {
            return JsonResultUtil.error(1010,"关闭失败，请联系管理员");
        }
    }

    @Override
    public JsonResult deleteBatchQuestions(String questionIds) {
        String[] idsStr = questionIds.split(",");
        for (String s : idsStr) {
            long id = Long.parseLong(s);
            questionDao.deleteQuestion(id);
        }
        return JsonResultUtil.success("删除成功");
    }


}
