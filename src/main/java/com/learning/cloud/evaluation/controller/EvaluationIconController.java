package com.learning.cloud.evaluation.controller;

import com.learning.cloud.evaluation.entity.EvaluationIcon;
import com.learning.cloud.evaluation.service.EvaluationIconService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class EvaluationIconController {

    @Autowired
    private EvaluationIconService iconService;

    @GetMapping("/getEvaluationIcon")
    public JsonResult getEvaluationIcon(EvaluationIcon evaluationIcon) {
        return iconService.getEvaluationIcon(evaluationIcon);
    }

    @GetMapping("/listEvaluationIconWithDefault")
    public JsonResult listEvaluationIconWithDefault(Integer schoolId, Integer iconType) {
        return iconService.listEvaluationIconWithDefault(schoolId, iconType);
    }

    @GetMapping("/getEvaluationIconById")
    public void getEvaluationIconById(Long id, HttpServletResponse response) throws IOException {
        EvaluationIcon evaluationIconById = iconService.getEvaluationIconById(id);
        if (evaluationIconById == null) {
            return;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(evaluationIconById.getPic());
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {
                bytes[i] += 256;
            }
        }
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);

        out.flush();
        out.close();
    }

    @PostMapping("/addEvaluationIcon")
    public JsonResult addEvaluationIcon(@RequestParam(value = "file", required = false) MultipartFile file, EvaluationIcon evaluationIcon) throws Exception {
        return iconService.addEvaluationIcon(file, evaluationIcon);
    }

    @PostMapping("/updateEvaluationIcon")
    public JsonResult updateEvaluationIcon(EvaluationIcon evaluationIcon) throws Exception {
        return iconService.updateEvaluationIcon(evaluationIcon);
    }

    @GetMapping("/deleteEvaluationIconById")
    public JsonResult deleteEvaluationIconById(Long id) {
        return iconService.deleteEvaluationIconById(id);
    }
}

