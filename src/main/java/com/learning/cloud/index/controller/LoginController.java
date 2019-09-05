package com.learning.cloud.index.controller;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.index.entity.SysUserInfo;
import com.learning.cloud.index.entity.UserInfo;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.MyException;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.learning.cloud.dingCommon.DingUtils.*;

/**
 * 免登
 */
@RestController
public class LoginController {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private BureauDao bureauDao;

    @Autowired
    private UserDao userDao;

    /**
     * 应用管理后台免登
     * 1.配置微应用后台地址
     * 2.获取免登过程中密钥（SSOSecret）
     * 3.1获取免登授权码
     *
     */
    @PostMapping("/oaLogin")
    public JsonResult oaLogin(@RequestParam(value="code",required = true) String code)throws Exception{
        return getAdministrator(code);
    }

    /**
     * 小程序免登
     * 1.前端获取免登授权码authCode（授权码，有效期5分钟，且只能使用一次）
     * userid
     */
    @PostMapping("/miniThirdPartyLogin")
    public JsonResult miniThirdPartyLogin(@RequestParam(value="authCode",required = true) String authCode, @RequestParam(value="corpid",required = true) String corpid) throws Exception{
        return getUserInfo(authCode, corpid);
    }

    /**
     * 扫码登录第三方网站
     * 1.获取appId及appSecret
     * 2.构造扫码登录页面
     * 3.服务端通过临时授权码获取授权用户的个人信息
     * openid
     */
    @PostMapping("/scanCodeThirdPartyLogin")
    public JsonResult scanCodeThirdPartyLogin(@RequestParam(value="authCode",required = true) String authCode,
                                              @RequestParam(value="appId",required = true) String appId,
                                              @RequestParam(value="appSecret",required = true) String appSecret) throws Exception{
        return getUserInfoByCode(authCode, appId, appSecret);
    }

    /**
     * 获取用户在当前系统的角色
     */
    @GetMapping("/getSysUserRole")
    public JsonResult getSysUserRole(String userid, String unionid, String corpid)throws Exception{
        // 返回值
        UserInfo userInfo = new UserInfo();
        List<User> users = new ArrayList<>();
        // 扫码登录
        if((corpid==null||corpid.isEmpty())&&!unionid.isEmpty()){
            //unionid 取数据库数据
            users = userDao.getByUnionId(unionid);
            userid = users.get(0).getUserId();
            corpid = users.get(0).getCorpId();
        }
        // 钉钉第三方登录,管理后台登录
        else if(!corpid.isEmpty()&&!userid.isEmpty()){
            //参数正常
            User user = userDao.getUserByUserIdAndCorpId(userid, corpid);
            users.add(user);
        }
        // 管理后台登录
        else if(!corpid.isEmpty()&&!unionid.isEmpty()){
            //获取userid
            userid = getUseridByUnionid(unionid, corpid);
        }
        else {
            throw new MyException(JsonResultEnum.NO_USER);
        }
        // 当用户处在多个组织时
        List<SysUserInfo> sysUserInfos = new ArrayList<>();
        for(int i=0;i<users.size();i++){
            User user = users.get(i);
            OapiUserGetResponse userGetResponse = getUserByUserid(userid, corpid);
            if(i==0){
                userInfo.setAvatar(userGetResponse.getAvatar());
                userInfo.setName(userGetResponse.getName());
                userInfo.setUnionid(unionid);
            }
            SysUserInfo sysUserInfo = new SysUserInfo();
            sysUserInfo.setCropid(user.getCorpId());
            sysUserInfo.setUserid(user.getUserId());
            sysUserInfo.setAdmin(userGetResponse.getIsAdmin());
            sysUserInfo.setRole(user.getRoleType());
            sysUserInfo.setId(user.getId());
            String cropName = "";
            if(user.getSchoolId()==null||user.getSchoolId()==-1){
                // 获取教育局id
                Bureau bureau = bureauDao.getByCorpId(corpid);
                sysUserInfo.setBureauId(bureau.getId());
                cropName = bureau.getBureauName();
            }else {
                School school = schoolDao.getSchoolByCorpId(corpid);
                cropName = school.getSchoolName();
                sysUserInfo.setBureauId(school.getBureauId());
                sysUserInfo.setSchoolId(user.getSchoolId());
            }
            sysUserInfo.setCropName(cropName);
            sysUserInfos.add(sysUserInfo);
        }
        userInfo.setSysUserInfos(sysUserInfos);
        return JsonResultUtil.success(userInfo);

    }
}
