package com.learning.cloud.supervision.controller;

import com.learning.cloud.supervision.entity.Supervision;
import com.learning.cloud.supervision.service.SupervisionService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SupervisionController {
    @Autowired
    private SupervisionService supervisionService;

    @GetMapping("/getSupervision")
    public JsonResult getSupervision(Supervision supervision){
        return supervisionService.getSupervision(supervision);
    }

    // 教育局获取所有学校提交的，审批后以及已撤销的新闻
    @GetMapping("/getBureauSupervision")
    public JsonResult getBureauSupervision(Supervision supervision){
        return supervisionService.getBureauSupervision(supervision);
    }

    @GetMapping("/getAllValidSupervision")
    public JsonResult getAllValidSupervision(Supervision supervision){
        return supervisionService.getAllValidSupervision(supervision);
    }

    @GetMapping("/updateToppingInSupervision")
    public JsonResult updateToppingInSupervision(Long id){
        return supervisionService.updateTopping(id);
    }

    @GetMapping("/getSupervisionById")
    public JsonResult getSupervisionById(long id){
        return supervisionService.getSupervisionById(id);
    }

    @PostMapping("/addSupervision")
    public JsonResult addSupervision(@RequestParam(value="file",required = false)MultipartFile file, Supervision supervision) throws Exception {
        return supervisionService.addSupervision(file, supervision);
    }

    @PostMapping("/updateSupervision")
    public JsonResult updateSupervision(@RequestParam(value = "file",required = false) MultipartFile file, Supervision supervision)  throws Exception{
        return supervisionService.updateSupervision(file,supervision);
    }

    @GetMapping("removeSupervisionById")
    public JsonResult removeSupervisionById(Long id){
        return supervisionService.removeSupervisionById(id);
    }

    @GetMapping("/deleteSupervisionById")
    public JsonResult deleteSupervisionById(Long id){
        return supervisionService.deleteSupervisionById(id);
    }

    @PostMapping("/batchUpdateSupervision")
    public JsonResult batchUpdateSupervision(String ids,Integer status){
        return supervisionService.batchUpdateSupervision(ids,status);
    }

    @GetMapping("/getToppingSupervision")
    public JsonResult getToppingSupervision(Integer bureauId){
        return supervisionService.getToppingSupervision(bureauId);
    }
}
