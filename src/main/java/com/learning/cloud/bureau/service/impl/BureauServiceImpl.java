package com.learning.cloud.bureau.service.impl;

import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.bureau.service.BureauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BureauServiceImpl implements BureauService {

    @Autowired
    private BureauDao bureauDao;

    @Override
    public List<Bureau> getBureaus() {
        return bureauDao.getBureaus();
    }
}
