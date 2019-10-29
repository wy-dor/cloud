package com.learning.cloud.evaluation.service.impl;

import com.learning.cloud.ding.question.service.QuestionService;
import com.learning.cloud.evaluation.dao.EvaluationIconDao;
import com.learning.cloud.evaluation.entity.EvaluationIcon;
import com.learning.cloud.evaluation.service.EvaluationIconService;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
public class EvaluationIconServiceImpl implements EvaluationIconService {

    @Autowired
    private EvaluationIconDao iconDao;

    @Autowired
    private QuestionService questionService;

    @Override
    public JsonResult addEvaluationIcon(MultipartFile file, EvaluationIcon evaluationIcon) throws Exception {
        byte[] bytes = file.getBytes();
        if (bytes.length > 200 * 1024 * 8) {
            throw new Exception("图片大小限制200KB以内，请重新上传");
        }
        InputStream inputStream = null;
        inputStream = file.getInputStream();
        File toFile = new File(file.getOriginalFilename());
        questionService.inputStreamToFile(inputStream, toFile);
        inputStream.close();
        // 开始读取文件并进行压缩
        Image img = ImageIO.read(toFile);

        // 构造一个类型为预定义图像类型之一的 BufferedImage
        BufferedImage tag = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);

        //绘制图像
        //Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        tag.getGraphics().drawImage(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(tag, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(outputStream.toByteArray());
        evaluationIcon.setPic(encode);
        int i = iconDao.insert(evaluationIcon);
        Long id = evaluationIcon.getId();
        return JsonResultUtil.success("成功增加" + i + "条数据:id " + id);
    }

    @Override
    public JsonResult getEvaluationIcon(EvaluationIcon evaluationIcon) {
        List<EvaluationIcon> evaluationIconList = iconDao.getByIcon(evaluationIcon);
        return JsonResultUtil.success(new PageEntity<>(evaluationIconList));
    }

    @Override
    public EvaluationIcon getEvaluationIconById(Long id) {
        EvaluationIcon evaluationIcon = iconDao.getById(id);
        return evaluationIcon;
    }

    @Override
    public JsonResult deleteEvaluationIconById(Long id) {
        int i = iconDao.deleteById(id);
        if (i > 0) {
            return JsonResultUtil.success("删除成功");
        }
        return JsonResultUtil.success("删除失败");

    }

    @Override
    public JsonResult updateEvaluationIcon(EvaluationIcon evaluationIcon) throws Exception {
        int i = iconDao.update(evaluationIcon);
        return JsonResultUtil.success("成功修改" + i + "条数据");
    }

}
