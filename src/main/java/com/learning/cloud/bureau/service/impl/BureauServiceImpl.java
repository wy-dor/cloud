package com.learning.cloud.bureau.service.impl;

import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.bureau.service.BureauService;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,Object> getOrgInfoByCorpId(String corpId) {
        Map<String,Object> map = new HashMap<>();
        Integer bureauId;
        School byCorpId = schoolDao.getSchoolByCorpId(corpId);
        if(byCorpId == null){
            Bureau bureau = bureauDao.getByCorpId(corpId);
            bureauId = bureau.getId();
            map.put("isSchool",false);
        }else{
            bureauId = byCorpId.getBureauId();
            String schoolName = byCorpId.getSchoolName();
            map.put("isSchool",true);
            map.put("schoolName",schoolName);
        }
        map.put("bureauId",bureauId);
        return map;
    }
}
