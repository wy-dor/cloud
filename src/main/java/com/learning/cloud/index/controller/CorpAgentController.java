package com.learning.cloud.index.controller;

import com.learning.cloud.index.service.CorpAgentService;
import com.learning.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorpAgentController {

    @Autowired
    private CorpAgentService corpAgentService;

    @GetMapping("/getAgentId")
    public JsonResult getAgentId(String corpId) {
        return corpAgentService.getAgentId(corpId);
    }
}
