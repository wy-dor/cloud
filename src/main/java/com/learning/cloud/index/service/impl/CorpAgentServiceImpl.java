package com.learning.cloud.index.service.impl;

import com.learning.cloud.index.dao.AuthCorpInfoDao;
import com.learning.cloud.index.dao.CorpAgentDao;
import com.learning.cloud.index.entity.AuthCorpInfo;
import com.learning.cloud.index.entity.CorpAgent;
import com.learning.cloud.index.service.CorpAgentService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CorpAgentServiceImpl implements CorpAgentService {

    @Autowired
    private CorpAgentDao corpAgentDao;

    @Autowired
    private AuthCorpInfoDao authCorpInfoDao;

    @Override
    public String getAgentId(String corpId) {
        CorpAgent byCorpId = corpAgentDao.getByCorpId(corpId);
        String agentId = byCorpId.getAgentId();
        return agentId;
    }

    public Boolean getIsSchool(String corpId){
        AuthCorpInfo info = authCorpInfoDao.getCorpInfoByCorpId(corpId);
        Boolean flag = false;
        if(info.getIndustry().equals("初中等教育")){
            flag = true;
        }
        return flag;
    }
}
