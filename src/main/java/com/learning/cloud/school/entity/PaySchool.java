package com.learning.cloud.school.entity;

import com.learning.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaySchool extends BaseEntity {
    private Integer id;

    private Integer schoolId;

    @NotNull(message = "学校名称不能为空")
    private String schoolName;

    @NotNull(message = "学校类型不能为空")
    private String schoolType;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String districtCode;

    private String districtName;

    private String schoolPid;

    private String schoolNo;

    private String schoolIcon;

    private String schoolIconType;

    private String schoolStdCode;

    private String startTime;

    private String endTime;

    private String month;

    private BigDecimal totalBillAmount;

    private Integer authK12;

    private String appAuthToken;

}
