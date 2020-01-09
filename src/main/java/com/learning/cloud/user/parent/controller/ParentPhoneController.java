package com.learning.cloud.user.parent.controller;

import com.learning.cloud.user.parent.entity.ParentPhone;
import com.learning.cloud.user.parent.service.ParentPhoneService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParentPhoneController {

    @Autowired
    private ParentPhoneService parentPhoneService;

    @GetMapping("/getParentPhoneByUserId")
    public JsonResult getParentPhoneByUserId(String userId) {
        return parentPhoneService.getParentPhoneByUserId(userId);
    }

    @GetMapping("/getParentPhoneById")
    public JsonResult getParentPhoneById(Long id) {
        return parentPhoneService.getParentPhoneById(id);
    }

    @PostMapping("/addParentPhone")
    public JsonResult addParentPhone(ParentPhone parentPhone) {
        return parentPhoneService.addParentPhone(parentPhone);
    }

    @PostMapping("/updateParentPhone")
    public JsonResult updateParentPhone(ParentPhone parentPhone) {
        return parentPhoneService.updateParentPhone(parentPhone);
    }

    @GetMapping("/deleteParentPhoneById")
    public JsonResult deleteParentPhoneById(Integer id) {
        return parentPhoneService.deleteParentPhoneById(id);
    }

}
