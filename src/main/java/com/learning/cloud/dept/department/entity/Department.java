package com.learning.cloud.dept.department.entity;

import com.learning.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class Department extends BaseEntity {
    private Long id;

    private String deptId;

    private String name;

    private String parentId;

    private Short outerDept;

    private String deptManagerUseridList;

    private Short groupContainSubDept;

    @Getter
    @Setter
    private Short deptHiding;

    @Getter
    @Setter
    private String corpId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Short getOuterDept() {
        return outerDept;
    }

    public void setOuterDept(Short outerDept) {
        this.outerDept = outerDept;
    }

    public String getDeptManagerUseridList() {
        return deptManagerUseridList;
    }

    public void setDeptManagerUseridList(String deptManagerUseridList) {
        this.deptManagerUseridList = deptManagerUseridList == null ? null : deptManagerUseridList.trim();
    }

    public Short getGroupContainSubDept() {
        return groupContainSubDept;
    }

    public void setGroupContainSubDept(Short groupContainSubDept) {
        this.groupContainSubDept = groupContainSubDept;
    }
}
