package com.learning.cloud.user.parent.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import java.util.List;

@Data
public class Parent extends BaseEntity {
    private Integer id;

    private String parentName;

    private String userId;

    private String classId;

    private Integer campusId;

    private Integer schoolId;

    private Integer bureauId;

    private String avatar;

    //added
    private List<String> classIdStrList;

}
