package com.learning.cloud.index.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: yyt
 * @Date: 2019-09-03 17:16
 * @Desc: 用户登录后获取详情信息
 */
@Data
public class UserInfo {
    private String unionid;
    private String name;
    private String avatar;
    private List<SysUserInfo> sysUserInfos;

}
