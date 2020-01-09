package com.learning.cloud.bill.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PreBill extends BaseEntity {
    private Integer id;

    private Integer parentId;

    @NotNull(message = "学校信息无法获取")
    private Integer schoolId;

    private Integer campusId;

    private String studentUserId;

    @NotNull(message = "学生姓名必输")
    private String studentName;

    @NotNull(message = "金额必输")
    private BigDecimal amount;

    private String chargeBillTitle;

    private Date lastTime;

    private Integer classId;

    @NotNull(message = "班级必输")
    private String gradeClass;

    private String chargeItem;

}
