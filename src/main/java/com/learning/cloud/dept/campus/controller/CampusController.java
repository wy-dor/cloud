package com.learning.cloud.dept.campus.controller;

import com.learning.cloud.dept.campus.service.CampusService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampusController {
    @Autowired
    private CampusService campusService;

    /*获取学校下所有的校区列表*/
    @GetMapping("/getSchoolCampuses")
    public JsonResult getSchoolCampuses(Integer schoolId) {
        return campusService.getSchoolCampuses(schoolId);
    }
}
