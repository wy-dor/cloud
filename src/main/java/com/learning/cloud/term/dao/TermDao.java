package com.learning.cloud.term.dao;

import com.learning.cloud.term.entity.Term;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TermDao {
    List<Term> getSchoolTerm(@Param("schoolId") Long schoolId, @Param("year") String year);

    int editSchoolTerm(Term term);

    int addTerm(Term term_f);
}
