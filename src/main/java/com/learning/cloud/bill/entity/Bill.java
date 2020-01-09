package com.learning.cloud.bill.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Bill extends BaseEntity {
    private Integer id;

    private Integer parentId;

    @NotNull(message = "学校信息无法获取")
    private Integer schoolId;

    private Integer campusId;

    private Integer studentId;

    @NotNull(message = "学生姓名必输")
    private String studentName;

    @NotNull(message = "金额必输")
    private BigDecimal amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private String gmtEnd;

    private String endEnable;

    private String isvTradeNo;

    private String chargeBillTitle;

    private Date createTime;

    private String remark;

    private String orderNo;

    private String tradeNo;

    private String tradeStatus;

    private Date lastTime;

    private Integer classId;

    @NotNull(message = "班级必输")
    private String gradeClass;

    @NotNull(message = "家长手机号必输")
    private String parentPhone;

    @NotNull(message = "支付类型出错，请使用指定应用支付")
    private Short payType;

    private Short state;

    private String chargeItem;

    //微信参数
    private String code;

    private String classIds;

    //查询参数
    //账单创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date end;

    //账单付款时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date startC;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date endC;

    private String tradeInfo;

    private Integer preId;

}
