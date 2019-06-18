package com.learning.cloud.term.service.impl;

import com.learning.cloud.term.dao.TermDao;
import com.learning.cloud.term.entity.Term;
import com.learning.cloud.term.service.TermService;
import com.learning.domain.JsonResult;
import com.learning.utils.JsonResultUtil;
import jdk.nashorn.internal.ir.LiteralNode;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.learning.cloud.util.Utils.getYear;

@Service
public class TermServiceImpl implements TermService {

    @Autowired
    private TermDao termDao;

    @Override
    public JsonResult getSchoolTerm(Long schoolId) throws Exception {
        List<Term> terms = termDao.getSchoolTerm(schoolId);
        if(terms==null||terms.size()<=0){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //自动插入学校的学期
            //学期名称 (year-1)-year;year-（year+1）
            String termName_F = getYear(-1)+"-"+getYear(0)+"学年 第二学期";
            String start_F = getYear(0)+"-02-15";
            String end_F = getYear(0)+"-06-30";
            Term term_F = new Term();
            term_F.setTermName(termName_F);
            term_F.setSchoolId(schoolId);
            term_F.setStart(format.parse(start_F));
            term_F.setEnd(format.parse(end_F));
            int i = termDao.addTerm(term_F);
            String termName_S = getYear(0)+"-"+getYear(1)+"学年 第一学期";
            String start_S = getYear(0)+"-09-01";
            String end_S = getYear(1)+"-01-30";
            Term term_S = new Term();
            term_S.setTermName(termName_S);
            term_S.setSchoolId(schoolId);
            term_S.setStart(format.parse(start_S));
            term_S.setEnd(format.parse(end_S));
            int j = termDao.addTerm(term_S);
            terms.add(term_S);
            terms.add(term_F);
        }
        return JsonResultUtil.success(terms);
    }

    @Override
    public JsonResult editSchoolTerm(Term term) throws Exception {
        int i = termDao.editSchoolTerm(term);
        return null;
    }
}
