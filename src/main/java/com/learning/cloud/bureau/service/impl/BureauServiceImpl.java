package com.learning.cloud.bureau.service.impl;

import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.bureau.service.BureauService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BureauServiceImpl implements BureauService {

    @Autowired
    private BureauDao bureauDao;

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public List<Bureau> getBureaus() {
        return bureauDao.getBureaus();
    }

    @Override
    public JsonResult getBureauIdByCorpId(String corpId) {
        Integer bureauId;
        School byCorpId = schoolDao.getSchoolByCorpId(corpId);
        if(byCorpId == null){
            Bureau bureau = bureauDao.getByCorpId(corpId);
            bureauId = bureau.getId();
        }else{
            bureauId = byCorpId.getBureauId();
        }
        return JsonResultUtil.success(bureauId);
    }
}
