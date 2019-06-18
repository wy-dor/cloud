package com.learning.domain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author: yyt
 * @Date: 2018-12-26 18:21
 * @Desc:
 */
public abstract class BaseEntity {

    //分页大小
    private Integer pageSize;
    //分页开始
    private Integer pageNum;

    private Integer total;

    //排序类型DESC  or  AES
    private String sort;

    private String orderBy;

    @JSONField(serialize = false)
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    @JSONField(serialize = false)
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @JSONField(serialize = false)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @JSONField(serialize = false)
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @JSONField(serialize = false)
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
